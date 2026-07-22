package ru.gitverse.bizzareowl.mgateway.gateway;

import java.util.Map;
import java.util.Objects;

public final class AlertGateway {

    private final Map<Integer, AlertMessageHandler> sourceIdToHandlersMap;

    public AlertGateway(Map<Integer, AlertMessageHandler> sourceIdToHandlersMap) {
        if (Objects.requireNonNull(sourceIdToHandlersMap).isEmpty()) {
            throw new IllegalArgumentException("Source id's to alert messages handlers map cannot be empty. Provide map with at least one alert message handler");
        }

        this.sourceIdToHandlersMap = sourceIdToHandlersMap;
    }

    public void processMessage(final RawAlertMessage rawAlertMessage) {
        if (!this.sourceIdToHandlersMap.containsKey(rawAlertMessage.sourceId)) {
            throw new IllegalArgumentException(String.format("Unknown source of alert message: %d", rawAlertMessage.sourceId));
        }

        if (rawAlertMessage.body == null) {
            throw new IllegalArgumentException("Raw message must contain non-null body");
        }

        if (rawAlertMessage.body.length == 0) {
            throw new IllegalArgumentException("Raw message body cannot be empty");
        }

        this.sourceIdToHandlersMap.get(rawAlertMessage.sourceId).handle(rawAlertMessage);
    }

}
