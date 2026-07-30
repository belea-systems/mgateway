package ru.gitverse.bizzareowl.mgateway.gateway;

import ru.gitverse.bizzareowl.mgateway.messaging.AlertMessageSender;

import java.util.Objects;

public abstract class AlertMessageHandler {

    private final AlertMessageSender alertMessageSender;

    public AlertMessageHandler(final AlertMessageSender alertMessageSender) {
        this.alertMessageSender = alertMessageSender;
    }

    protected abstract ProcessedAlertMessage processMessage(final RawAlertMessage rawAlertMessage);

    public ProcessedAlertMessage handle(final RawAlertMessage rawAlertMessage) {
        final ProcessedAlertMessage processedAlertMessage = processMessage(Objects.requireNonNull(rawAlertMessage));
        alertMessageSender.sendProcessedMessage(processedAlertMessage);
        return processedAlertMessage;
    }

}
