package ru.gitverse.bizzareowl.mgateway.messaging;

public interface AlertMessageSender {
    void sendProcessedMessage(final ProcessedAlertMessage processedAlertMessage);
}
