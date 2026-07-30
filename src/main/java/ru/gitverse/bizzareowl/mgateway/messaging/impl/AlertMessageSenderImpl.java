package ru.gitverse.bizzareowl.mgateway.messaging.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;
import ru.gitverse.bizzareowl.mgateway.messaging.AlertMessageSender;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlertMessageSenderImpl implements AlertMessageSender {

    @Value("${application.alert-messages-topic}")
    private String alertMessagesTopic;

    private final KafkaTemplate<UUID, ProcessedAlertMessage> kafkaTemplate;

    @Override
    public void sendProcessedMessage(ProcessedAlertMessage processedAlertMessage) {
        kafkaTemplate.send(alertMessagesTopic, processedAlertMessage.id(), processedAlertMessage);
    }
}
