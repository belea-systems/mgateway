package ru.gitverse.bizzareowl.mgateway.messaging.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;
import ru.gitverse.bizzareowl.mgateway.messaging.AlertMessageSender;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlertMessageSenderImpl implements AlertMessageSender {

    @Value("${application.alert-messages-topic}")
    private String alertMessagesTopic;

    private final KafkaTemplate<UUID, ProcessedAlertMessage> kafkaTemplate;

    @Override
    public void sendProcessedMessage(ProcessedAlertMessage processedAlertMessage) {
        if (processedAlertMessage == null) {
            throw new IllegalArgumentException("Processed message cannot be null");
        }

        if (processedAlertMessage.id() == null) {
            throw new IllegalArgumentException("Processed message UUID cannot be null and must be valid UUID");
        }

        if (processedAlertMessage.message() == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        if (processedAlertMessage.sentAt().isAfter(Instant.now())) {
            throw new IllegalArgumentException("Illegal receiving time value");
        }

        kafkaTemplate.send(alertMessagesTopic, processedAlertMessage.id(), processedAlertMessage);
    }
}
