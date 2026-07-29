package ru.gitverse.bizzareowl.mgateway.gateway.impl;

import org.springframework.stereotype.Component;
import ru.gitverse.bizzareowl.mgateway.gateway.AlertMessageHandler;
import ru.gitverse.bizzareowl.mgateway.gateway.RawAlertMessage;
import ru.gitverse.bizzareowl.mgateway.messaging.AlertMessageSender;
import ru.gitverse.bizzareowl.mgateway.messaging.ProcessedAlertMessage;

@Component
public class TelegramAlertMessageHandler extends AlertMessageHandler {

    public TelegramAlertMessageHandler(AlertMessageSender alertMessageSender) {
        super(alertMessageSender);
    }

    @Override
    protected ProcessedAlertMessage processMessage(RawAlertMessage rawAlertMessage) {
        return null;
    }
}
