package ru.gitverse.bizzareowl.mgateway.gateway;

import org.springframework.stereotype.Component;
import ru.gitverse.bizzareowl.mgateway.configuration.properties.AlertMessagesToHandlersProperties;

import java.util.Map;

@Component
public final class AlertMessagesToHandlerRouter {

    private final Map<Integer, AlertMessageHandler> alertMessageHandlerMap;

    public AlertMessagesToHandlerRouter(final AlertMessagesToHandlersProperties alertMessagesToHandlersProperties) {
        alertMessageHandlerMap = null;
    }


}
