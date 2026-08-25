package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_assignments")
public class PermissionJpaEntity {

	 @Id
    @Column(name = "code", length = 100)
    private String code;

    @Column(name = "description", length = 255)
    private String description;

    protected PermissionJpaEntity() {}

    public PermissionJpaEntity(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() { return code; }
    public String getDescription() { return description; }
}
