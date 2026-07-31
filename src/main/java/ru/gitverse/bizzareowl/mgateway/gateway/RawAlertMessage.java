package ru.gitverse.bizzareowl.mgateway.gateway;

import java.time.Instant;

public record RawAlertMessage(String message, String messageId, Instant sentAt, MessageSourceData messageData) {
}
