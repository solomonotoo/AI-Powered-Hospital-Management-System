package com.ai_powered_hms_backend.identity.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.ai_powered_hms_backend.identity.domain.valueobjects.HashedPassword;
import com.ai_powered_hms_backend.shared_kernel.base.AggregateRoot;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.AuditMetadata;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;

/**
 * UserCredential is keyed by StaffId directly (1:1 with StaffProfile, in a
 * separate bounded context — deliberate shared-identity pattern).
 */
public class UserCredential extends AggregateRoot<StaffId> {

	private Email loginEmail;
	private HashedPassword passwordHash;
	private boolean mfaEnabled;
	private boolean mustChangePassword;
	private LocalDateTime lastLoginAt;
	private boolean active;
	private AuditMetadata audit;

	// constructor for create a new user credentials
	private UserCredential(StaffId id, Email loginEmail, HashedPassword passwordHash, UUID createdBy) {
		super(id);
		this.loginEmail = Objects.requireNonNull(loginEmail, "Login email is required");
		this.passwordHash = Objects.requireNonNull(passwordHash, "Password hash is required");
		this.mfaEnabled = false;
		this.mustChangePassword = true; // temporary password set by admin — force change on first login
		this.active = true;
		this.audit = AuditMetadata.create(createdBy);
	}

	// constructor for reconstitute or rehydration. it purpose is to recreate user
	// credentials form previously
	// persisted state.
	private UserCredential(StaffId id, Email loginEmail, HashedPassword passwordHash, boolean mfaEnabled,
			boolean mustChangePassword, LocalDateTime lastLoginAt, boolean active, AuditMetadata audit) {
		super(id);
		this.loginEmail = loginEmail;
		this.passwordHash = passwordHash;
		this.mfaEnabled = mfaEnabled;
		this.mustChangePassword = mustChangePassword;
		this.lastLoginAt = lastLoginAt;
		this.active = active;
		this.audit = audit;
	}

	// factory method for creating new user credentials
	public static UserCredential create(StaffId staffId, Email loginEmail, HashedPassword passwordHash,
			UUID createdBy) {
		return new UserCredential(staffId, loginEmail, passwordHash, createdBy);
	}

	// factory method for rehydration or reconstitution
	public static UserCredential reconstitute(StaffId id, Email loginEmail, HashedPassword passwordHash,
			boolean mfaEnabled, boolean mustChangePassword, LocalDateTime lastLoginAt, boolean active,
			AuditMetadata audit) {
		return new UserCredential(id, loginEmail, passwordHash, mfaEnabled, mustChangePassword, lastLoginAt, active,
				audit);
	}

	// This is also domain behavior, but you might consider whether logging in
	// should affect more than just lastLoginAt.
	// verify the account is active
//	 clear failed login attempts
//	 update audit information
//	 raise a domain event such as UserLoggedIn
	public void recordSuccessfulLogin() {
		this.lastLoginAt = LocalDateTime.now();
	}

	// business behaviour that implement a business rule
	// When a password is changed successfully, the user no longer needs to change
	// it at next login.
	public void changePassword(HashedPassword newHash, UUID modifiedBy) {
		this.passwordHash = Objects.requireNonNull(newHash, "New password hash is required");
		this.mustChangePassword = false;
		recordChangeBy(modifiedBy);
	}

	public void deactivate(UUID modifiedBy) {
		this.active = false;
		recordChangeBy(modifiedBy);
	}

	public void reactivate(UUID modifiedBy) {
		this.active = true;
		recordChangeBy(modifiedBy);
	}

	private void recordChangeBy(UUID modifiedBy) {
		Objects.requireNonNull(modifiedBy, "Modified by must not be null");
		audit.update(modifiedBy);
	}

	// accessors or getters
	public StaffId staffId() {
		return getId();
	}

	public Email loginEmail() {
		return loginEmail;
	}

	public HashedPassword passwordHash() {
		return passwordHash;
	}

	public boolean isMfaEnabled() {
		return mfaEnabled;
	}

	public boolean mustChangePassword() {
		return mustChangePassword;
	}

	public LocalDateTime lastLoginAt() {
		return lastLoginAt;
	}

	public boolean isActive() {
		return active;
	}

	public AuditMetadata audit() {
		return audit;
	}

}
