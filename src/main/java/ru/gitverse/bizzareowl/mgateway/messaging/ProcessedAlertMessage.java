package ru.gitverse.bizzareowl.mgateway.messaging;

import java.time.ZonedDateTime;
import java.util.UUID;

public class ProcessedAlertMessage {
    private UUID id;
    private int sourceId;
    private String message;
    private ZonedDateTime receivingTime;
    private Object optionalData;
}
