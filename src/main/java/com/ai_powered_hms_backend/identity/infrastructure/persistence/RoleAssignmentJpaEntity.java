package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataEmbeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_assignments")
public class RoleAssignmentJpaEntity {

	@Id
    private UUID id;

    @Column(name = "staff_id", nullable = false)
    private UUID staffId;

    @Column(name = "role_id", nullable = false)
    private UUID roleId;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @Embedded
    AuditMetadataEmbeddable audit;

	public RoleAssignmentJpaEntity(UUID id, UUID staffId, UUID roleId, LocalDateTime expiresAt, boolean revoked,
			AuditMetadataEmbeddable audit) {
		super();
		this.id = id;
		this.staffId = staffId;
		this.roleId = roleId;
		this.expiresAt = expiresAt;
		this.revoked = revoked;
		this.audit = audit;
	}

	public UUID getId() {
		return id;
	}

	public UUID getStaffId() {
		return staffId;
	}

	public UUID getRoleId() {
		return roleId;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public AuditMetadataEmbeddable getAudit() {
		return audit;
	}
    
    
}
