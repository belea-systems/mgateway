package ru.gitverse.bizzareowl.mgateway.persistence;

public interface DeduplicationRepository {
    boolean exists(Object o);
}
