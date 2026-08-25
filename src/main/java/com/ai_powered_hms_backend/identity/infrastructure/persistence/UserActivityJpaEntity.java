package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_activity")
public class UserActivityJpaEntity {

    @Id
    private UUID id;

    @Column(name = "staff_id", nullable = false)
    private UUID staffId;

    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "actor_id")
    private UUID actorId;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    protected UserActivityJpaEntity() {}

    public UserActivityJpaEntity(UUID id, UUID staffId, String eventType, String description, UUID actorId, LocalDateTime occurredAt) {
        this.id = id;
        this.staffId = staffId;
        this.eventType = eventType;
        this.description = description;
        this.actorId = actorId;
        this.occurredAt = occurredAt;
    }

    public UUID getId() { return id; }
    public UUID getStaffId() { return staffId; }
    public String getEventType() { return eventType; }
    public String getDescription() { return description; }
    public UUID getActorId() { return actorId; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
}
