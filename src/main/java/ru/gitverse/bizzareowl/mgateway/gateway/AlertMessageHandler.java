package ru.gitverse.bizzareowl.mgateway.gateway;

import org.springframework.stereotype.Component;
import ru.gitverse.bizzareowl.mgateway.messaging.AlertMessageSender;

import java.util.Objects;
import java.util.UUID;

@Component
public class AlertMessageHandler {

    protected final AlertMessageSender alertMessageSender;

    public AlertMessageHandler(final AlertMessageSender alertMessageSender) {
        this.alertMessageSender = alertMessageSender;
    }

    protected ProcessedAlertMessage processMessage(final RawAlertMessage rawAlertMessage) {
        if (rawAlertMessage == null) {
            throw new IllegalArgumentException("Alert message cannot be null");
        }

        if (rawAlertMessage.message() == null || rawAlertMessage.message().isBlank()) {
            throw new IllegalArgumentException("Message must contain alert or some text information");
        }

        return new ProcessedAlertMessage(
                UUID.randomUUID(), rawAlertMessage.message(), rawAlertMessage.messageId(), rawAlertMessage.messageData(), rawAlertMessage.sentAt()
        );
    }

    public ProcessedAlertMessage handle(final RawAlertMessage rawAlertMessage) {
        final ProcessedAlertMessage processedAlertMessage = processMessage(Objects.requireNonNull(rawAlertMessage));
        alertMessageSender.sendProcessedMessage(processedAlertMessage);
        return processedAlertMessage;
    }

}
