package ru.gitverse.bizzareowl.mgateway.integration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jpa.test.autoconfigure.AutoConfigureTestEntityManager;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import ru.gitverse.bizzareowl.mgateway.gateway.MessageSource;
import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;
import ru.gitverse.bizzareowl.mgateway.persistence.entities.MessageDeduplicationRecord;
import ru.gitverse.bizzareowl.mgateway.persistence.entities.MessageDeduplicationRecordPrimaryKey;
import ru.gitverse.bizzareowl.mgateway.web.dto.MessageSourceDataDto;
import ru.gitverse.bizzareowl.mgateway.web.dto.RawAlertMessageDto;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(topics = {
        "${application.alert-messages-topic}"
})
@Testcontainers
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestEntityManager
@ActiveProfiles("test-e2e")
public class E2EIntegrationTests {

    @SuppressWarnings("unused")
    @ServiceConnection
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:18");

    @Value("${application.alert-messages-topic}")
    private String alertMessagesTopic;

    @Autowired
    private MockMvcTester mockMvcTester;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ConsumerFactory<UUID, ProcessedAlertMessage> consumerFactory;


    @Test
    @DisplayName("End-to-end integration test for testing data flow")
    public void e2eIntegrationTest() {

        final RawAlertMessageDto rawAlertMessageDto = new RawAlertMessageDto(
                "Sample message", "sample-message-id", Instant.now(), new MessageSourceDataDto(
                "sample-source-id", "sample-source-name", MessageSource.TELEGRAM.name()
        ));

        final Consumer<UUID, ProcessedAlertMessage> consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, alertMessagesTopic);

        final MvcTestResult mvcTestResult = mockMvcTester.post()
                .uri("/emergency-alerts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(rawAlertMessageDto))
                .exchange();

        assertThat(mvcTestResult).hasStatus(HttpStatus.CREATED);

        ConsumerRecord<UUID, ProcessedAlertMessage> consumerRecord = KafkaTestUtils.getSingleRecord(consumer, alertMessagesTopic);

        assertThat(mvcTestResult).bodyJson().extractingPath("$.messageId").isEqualTo(consumerRecord.key().toString());

        final MessageDeduplicationRecord deduplicationRecord = testEntityManager.find(
                MessageDeduplicationRecord.class, new MessageDeduplicationRecordPrimaryKey("sample-message-id", "sample-source-id", MessageSource.TELEGRAM)
        );

        assertThat(deduplicationRecord).isNotNull();
    }

    /**
     * Deserialize UUID from string, needs for test
     */
    @SuppressWarnings("unused")
    public static UUID parseUUIDDeserializer(String value) {
        return UUID.fromString(value);
    }

}
