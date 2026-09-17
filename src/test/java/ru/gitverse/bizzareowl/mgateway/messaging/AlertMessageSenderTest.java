package ru.gitverse.bizzareowl.mgateway.messaging;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import ru.gitverse.bizzareowl.mgateway.gateway.MessageSource;
import ru.gitverse.bizzareowl.mgateway.gateway.MessageSourceData;
import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;

import java.time.ZonedDateTime;
import java.util.UUID;


@SpringBootTest
@EmbeddedKafka(topics = {
        "${application.alert-messages-topic}"
})
@ActiveProfiles("test-kafka")
public class AlertMessageSenderTest {

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
        final ZonedDateTime receivingTime = ZonedDateTime.now();
        final ProcessedAlertMessage processedAlertMessage = new ProcessedAlertMessage(
        uuid, "message", new MessageSourceData("id", "name", MessageSource.TELEGRAM), receivingTime);

        final Consumer<UUID, ProcessedAlertMessage> consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, alertMessagesTopic);

        alertMessageSender.sendProcessedMessage(processedAlertMessage);

        ConsumerRecord<UUID, ProcessedAlertMessage> record = KafkaTestUtils.getSingleRecord(consumer, alertMessagesTopic);

        Assertions.assertThat(record.key()).isEqualTo(uuid);
        Assertions.assertThat(record.value().message()).isEqualTo("message");
        Assertions.assertThat(record.value().data().id()).isEqualTo("id");
        Assertions.assertThat(record.value().data().name()).isEqualTo("name");
        Assertions.assertThat(record.value().data().source()).isEqualTo(MessageSource.TELEGRAM);
        Assertions.assertThat(record.value().receivingTime()).isEqualTo(receivingTime);

    }

    public static UUID parseUUIDDeserializer(String value) {
        return UUID.fromString(value);
    }
}