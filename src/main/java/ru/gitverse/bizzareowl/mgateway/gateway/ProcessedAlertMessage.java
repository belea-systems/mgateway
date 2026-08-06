package ru.gitverse.bizzareowl.mgateway.gateway;

import java.time.Instant;
import java.util.UUID;

public record ProcessedAlertMessage(UUID id, String message, String messageId, MessageSourceData data, Instant sentAt) {
}
