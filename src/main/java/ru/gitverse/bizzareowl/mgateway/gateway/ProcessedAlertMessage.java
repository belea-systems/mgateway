package ru.gitverse.bizzareowl.mgateway.gateway;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ProcessedAlertMessage(UUID id, int sourceId, String message,
                                    ZonedDateTime receivingTime, Object optionalData) {
}
