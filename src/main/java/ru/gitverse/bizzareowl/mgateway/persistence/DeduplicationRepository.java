package ru.gitverse.bizzareowl.mgateway.persistence;

import org.springframework.data.repository.CrudRepository;
import ru.gitverse.bizzareowl.mgateway.persistence.entities.MessageDeduplicationRecord;
import ru.gitverse.bizzareowl.mgateway.persistence.entities.MessageDeduplicationRecordPrimaryKey;

public interface DeduplicationRepository extends CrudRepository<MessageDeduplicationRecord, MessageDeduplicationRecordPrimaryKey> {
}
