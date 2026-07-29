package ru.gitverse.bizzareowl.mgateway.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gitverse.bizzareowl.mgateway.configuration.properties.AlertMessagesToHandlersProperties;

import java.util.HashMap;
import java.util.Map;

@Component
public final class AlertMessagesToHandlerRouter {

    private final Map<Integer, AlertMessageHandler> alertMessageHandlerMap;

    private AlertMessageHandler alertMessageHandler;

    @Autowired
    public AlertMessagesToHandlerRouter(final AlertMessageHandler alertMessageHandler, final AlertMessagesToHandlersProperties alertMessagesToHandlersProperties) {
        this.alertMessageHandler = alertMessageHandler;
        this(alertMessagesToHandlersProperties);
    }

    private AlertMessagesToHandlerRouter(final AlertMessagesToHandlersProperties alertMessagesToHandlersProperties) {
        this.alertMessageHandlerMap = new HashMap<>();

        alertMessagesToHandlersProperties.getTelegram().forEach(id -> {
            alertMessageHandlerMap.putIfAbsent(id, alertMessageHandler);
        });
    }

    public AlertMessageHandler route(int sourceId) {
        if (!this.alertMessageHandlerMap.containsKey(sourceId)) {
            throw new IllegalArgumentException("Unknown source ID");
        }

        return this.alertMessageHandlerMap.get(sourceId);
    }


}
