package com.ai_powered_hms_backend.shared_kernel.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AggregateRoot<ID>
        extends BaseEntity<ID> {


    private final List<Object> domainEvents =
            new ArrayList<>();


    protected AggregateRoot(ID id) {

        super(id);
    }


    protected void registerEvent(
            Object event
    ) {

        domainEvents.add(
                Objects.requireNonNull(
                        event,
                        "Domain event must not be null"
                )
        );
    }


    public List<Object> pullDomainEvents() {

        if (domainEvents.isEmpty()) {

            return List.of();
        }


        List<Object> events =
                List.copyOf(
                        domainEvents
                );


        domainEvents.clear();


        return events;
    }
    
}