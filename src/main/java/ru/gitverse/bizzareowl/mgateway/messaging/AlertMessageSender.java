package ru.gitverse.bizzareowl.mgateway.messaging;

import ru.gitverse.bizzareowl.mgateway.gateway.ProcessedAlertMessage;

public interface AlertMessageSender {
    void sendProcessedMessage(final ProcessedAlertMessage processedAlertMessage);
}
