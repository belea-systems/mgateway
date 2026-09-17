package ru.gitverse.bizzareowl.mgateway.persistence.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import ru.gitverse.bizzareowl.mgateway.gateway.MessageSource;

@Embeddable
public record MessageDeduplicationRecordPrimaryKey(
        @Basic(optional = false) String messageId,
        @Basic(optional = false) String sourceId,
        @Enumerated(EnumType.STRING) MessageSource source
) {
}
