package ru.gitverse.bizzareowl.mgateway.persistence.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class MessageDeduplicationRecord {
    @EmbeddedId
    private MessageDeduplicationRecordPrimaryKey key;
}
