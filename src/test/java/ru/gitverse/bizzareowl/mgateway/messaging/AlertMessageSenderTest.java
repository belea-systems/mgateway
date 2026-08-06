package ru.gitverse.bizzareowl.mgateway.messaging;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.autoconfigure.DispatcherServletAutoConfiguration;
import org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.bind.annotation.RestController;
import ru.gitverse.bizzareowl.mgateway.gateway.MessageSource;
import ru.gitverse.bizzareowl.mgateway.gateway.MessageSourceData;
import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;
import ru.gitverse.bizzareowl.mgateway.persistence.DeduplicationRepository;

import java.time.Instant;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@EmbeddedKafka(topics = {
        "${application.alert-messages-topic}"
})
@ImportAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class
})
@ActiveProfiles("test-kafka")
public class AlertMessageSenderTest {

    // TODO: CHANGE TEST TO REMOVE THIS USELESS DEPENDENCY
    @MockitoBean
    private DeduplicationRepository deduplicationRepository;

    @Value("${application.alert-messages-topic}")
    private String alertMessagesTopic;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ConsumerFactory<UUID, ProcessedAlertMessage> consumerFactory;

    @Autowired
    private AlertMessageSender alertMessageSender;

    @Test
    @DisplayName("Send valid processed message should be sent to Kafka without error")
    public void sendProcessedMessage_withValidMessage_shouldSendToKafkaTopic() {
        final UUID uuid = UUID.randomUUID();
        final Instant sentAt = Instant.now();
        final ProcessedAlertMessage processedAlertMessage = new ProcessedAlertMessage(
        uuid, "message", "id", new MessageSourceData("id", "name", MessageSource.TELEGRAM), sentAt);

        final Consumer<UUID, ProcessedAlertMessage> consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, alertMessagesTopic);

        alertMessageSender.sendProcessedMessage(processedAlertMessage);

        ConsumerRecord<UUID, ProcessedAlertMessage> record = KafkaTestUtils.getSingleRecord(consumer, alertMessagesTopic);

        Assertions.assertThat(record.key()).isEqualTo(uuid);
        Assertions.assertThat(record.value().message()).isEqualTo("message");
        Assertions.assertThat(record.value().messageId()).isEqualTo("id");
        Assertions.assertThat(record.value().data().id()).isEqualTo("id");
        Assertions.assertThat(record.value().data().name()).isEqualTo("name");
        Assertions.assertThat(record.value().data().source()).isEqualTo(MessageSource.TELEGRAM);
        Assertions.assertThat(record.value().sentAt()).isEqualTo(sentAt);

    }

    /**
     * Deserialize UUID from string, needs for test
     */
    @SuppressWarnings("unused")
    public static UUID parseUUIDDeserializer(String value) {
        return UUID.fromString(value);
    }
}