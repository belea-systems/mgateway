package ru.gitverse.bizzareowl.mgateway.gateway;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ProcessedAlertMessage(UUID id, String message, MessageSourceData data, ZonedDateTime receivingTime) {
}
