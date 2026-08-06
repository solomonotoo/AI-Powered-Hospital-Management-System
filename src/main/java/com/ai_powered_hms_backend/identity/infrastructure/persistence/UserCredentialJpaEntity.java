package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ai_powered_hms_backend.identity.domain.valueobjects.HashedPassword;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataEmbeddable;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.EmailConverter;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_credentials")
public class UserCredentialJpaEntity {

	@Id
	private UUID staffId;
	
	@Convert(converter = EmailConverter.class)
	@Column(name = "login_email", nullable = false, unique = true, length = 150)
	private Email loginEmail;
	
	
	@Convert(converter = HashedPasswordConverter.class)
	@Column(name = "password_hash", nullable = false, length = 255)
	private HashedPassword passwordHash;
	
	@Column(name = "mfa_enabled", nullable = false)
	private boolean mfaEnabled;
	
	@Column(name = "must_change_password", nullable = false)
	private boolean mustChangePassword;
	
	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;
	
	@Column(name = "is_active", nullable = false)
	private boolean active;
	
	@Embedded
	private AuditMetadataEmbeddable audit;
	
	protected UserCredentialJpaEntity() {}

	public UserCredentialJpaEntity(UUID staffId, Email loginEmail, HashedPassword passwordHash, boolean mfaEnabled,
			boolean mustChangePassword, LocalDateTime lastLoginAt, boolean active, AuditMetadataEmbeddable audit) {
		super();
		this.staffId = staffId;
		this.loginEmail = loginEmail;
		this.passwordHash = passwordHash;
		this.mfaEnabled = mfaEnabled;
		this.mustChangePassword = mustChangePassword;
		this.lastLoginAt = lastLoginAt;
		this.active = active;
		this.audit = audit;
	}

	public UUID getStaffId() {
		return staffId;
	}

	public Email getLoginEmail() {
		return loginEmail;
	}

	public HashedPassword getPasswordHash() {
		return passwordHash;
	}

	public boolean isMfaEnabled() {
		return mfaEnabled;
	}

	public boolean isMustChangePassword() {
		return mustChangePassword;
	}

	public LocalDateTime getLastLoginAt() {
		return lastLoginAt;
	}

	public boolean isActive() {
		return active;
	}

	public AuditMetadataEmbeddable getAudit() {
		return audit;
	}
	
	
	
	
	
	
	
	
}
