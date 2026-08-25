package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_sessions")
public class UserSessionJpaEntity {

	@Id
	private UUID id;
	
	@Column(name = "staff_id", nullable = false)
	private UUID staffId;
	
	@Column(name = "issued_at", nullable = false)
	private LocalDateTime issuedAt;
	
	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;
	
	@Column(name = "user_agent", length = 255)
	private String userAgent;
	
	@Column(name = "revoked", nullable = false)
	private boolean revoked;
	
	protected UserSessionJpaEntity() {
		//JPA
	}

	public UserSessionJpaEntity(UUID id, UUID staffId, LocalDateTime issuedAt, LocalDateTime expiresAt,
			String userAgent, boolean revoked) {
		super();
		this.id = id;
		this.staffId = staffId;
		this.issuedAt = issuedAt;
		this.expiresAt = expiresAt;
		this.userAgent = userAgent;
		this.revoked = revoked;
	}

	public UUID getId() {
		return id;
	}

	public UUID getStaffId() {
		return staffId;
	}

	public LocalDateTime getIssuedAt() {
		return issuedAt;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public boolean isRevoked() {
		return revoked;
	}
	
	
	
	
	
}
