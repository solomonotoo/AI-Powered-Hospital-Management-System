package com.ai_powered_hms_backend.shared_kernel.base;

import java.util.Objects;

/**
 * Base class for domain entities.
 *
 * An Entity is identified by its identity, not by all of its attributes.
 *
 * @param <ID> strongly typed entity identifier
 */
public abstract class BaseEntity<ID> {

    protected ID id;

    /**
     * Required by JPA/Hibernate.
     */
//    protected BaseEntity() {
//    }

    /**
     * Used by domain constructors/factory methods.
     */
    protected BaseEntity(ID id) {
        this.id = Objects.requireNonNull(
                id,
                "Entity id must not be null"
        );
    }

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof BaseEntity<?> other)) {
            return false;
        }

        return id != null &&
                id.equals(other.id);
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(id);
    }
}