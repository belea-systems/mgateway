package ru.gitverse.bizzareowl.mgateway.gateway;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.gitverse.bizzareowl.mgateway.configuration.properties.AlertMessagesToHandlersProperties;

import java.util.HashMap;
import java.util.Map;

@Component
public final class AlertMessagesToHandlerRouter {

    @Getter
    private final Map<Integer, AlertMessageHandler> alertMessageHandlerMap;

    private final AlertMessageHandler alertMessageHandler;

    @Autowired
    public AlertMessagesToHandlerRouter(final AlertMessageHandler alertMessageHandler,
                                        final AlertMessagesToHandlersProperties alertMessagesToHandlersProperties) {

        this.alertMessageHandler = alertMessageHandler;
        this.alertMessageHandlerMap = new HashMap<>();

        alertMessagesToHandlersProperties.getSources().getTelegram().forEach(id -> {
            alertMessageHandlerMap.putIfAbsent(id, this.alertMessageHandler);
        });
    }

    public AlertMessageHandler route(int sourceId) {
        if (!this.alertMessageHandlerMap.containsKey(sourceId)) {
            throw new IllegalArgumentException("Unknown source ID");
        }

        return this.alertMessageHandlerMap.get(sourceId);
    }

}
