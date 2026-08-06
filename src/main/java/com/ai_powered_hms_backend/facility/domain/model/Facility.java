package com.ai_powered_hms_backend.facility.domain.model;

import java.util.Objects;
import java.util.UUID;

import com.ai_powered_hms_backend.facility.domain.eums.FacilityStatus;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;
import com.ai_powered_hms_backend.shared_kernel.base.AggregateRoot;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.EmailConverter;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.PhoneNumberConverter;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.AuditMetadata;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.FacilityCode;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;

/**
 * Facility Aggregate Root — represents a hospital branch (e.g. Korle-Bu
 * Teaching Hospital, Ridge Hospital, Tamale Teaching Hospital) under the
 * single-tenant HMS.
 *
 * Pure domain object — no Spring, no JPA/Hibernate, no persistence concerns.
 */
public class Facility extends AggregateRoot<FacilityId> {

	private FacilityCode code;
	private String name;
	private FacilityType type;
	private Address location;
	private PhoneNumber contactPhone;
	private Email contactEmail;
	private FacilityStatus status;
	private AuditMetadata audit;

	// ==========================================================
	// CONSTRUCTOR FOR NEW FACILITY ONBOARDING
	// ==========================================================

	private Facility(FacilityId id, FacilityCode code, String name, FacilityType type, Address location,
			PhoneNumber contactPhone, Email email, UUID createdBy) {

		super(id);
		this.code = Objects.requireNonNull(code, "Facility code is required");
		this.name = requireText(name, "Facility name");
		this.type = Objects.requireNonNull(type, "Facility type is required");
		this.location = Objects.requireNonNull(location, "Facility location is required");
		this.contactPhone = Objects.requireNonNull(contactPhone, "Contact phone is required");
		this.contactEmail = contactEmail; // optional — some facilities may not have a dedicated inbox yet

		Objects.requireNonNull(createdBy, "Created by is required");

		this.status = FacilityStatus.ACTIVE;
		this.audit = AuditMetadata.create(createdBy);

	}

	// ==========================================================
	// CONSTRUCTOR FOR RECONSTITUTING EXISTING FACILITY
	// ==========================================================
	public Facility(
	        FacilityId id,
	        FacilityCode code,
	        String name,
	        FacilityType type,
	        Address location,
	        PhoneNumber contactPhone,
	        Email contactEmail,
	        FacilityStatus status,
	        AuditMetadata audit
	) {
	    super(id);

	    this.code = Objects.requireNonNull(code, "Facility code is required");
	    this.name = requireText(name, "Facility name");
	    this.type = Objects.requireNonNull(type, "Facility type is required");
	    this.location = Objects.requireNonNull(location, "Facility location is required");
	    this.contactPhone = Objects.requireNonNull(contactPhone, "Contact phone is required");
	    this.contactEmail = contactEmail;
	    this.status = Objects.requireNonNull(status, "Facility status is required");
	    this.audit = Objects.requireNonNull(audit, "Audit metadata is required");
	}

	// ==========================================================
	// ONBOARD NEW FACILITY. onboarding generally means bringing a new entity into a system so it becomes recognized, configured, and ready for use.
	// ==========================================================
	public static Facility onboard(FacilityCode code, String name, FacilityType type, Address location,
			PhoneNumber contactPhone, Email contactEmail, UUID createdBy) {
		return new Facility(FacilityId.newId(), code, name, type, location, contactPhone, contactEmail, createdBy);
	}

	// ==========================================================
	// RECONSTITUTE (used by persistence mapper)
	// ==========================================================

	public static Facility reconstitute(FacilityId id, FacilityCode code, String name, FacilityType type,
			Address location, PhoneNumber contactPhone, Email contactEmail, FacilityStatus status,
			AuditMetadata audit) {
		return new Facility(id, code, name, type, location, contactPhone, contactEmail, status, audit);
	}

	// ==========================================================
	// COMMANDS
	// ==========================================================

	public void rename(String name, UUID modifiedBy) {
		this.name = requireText(name, "Facility nane");
		recordChangeBy(modifiedBy);
	}

	public void relocate(Address location, UUID modifiedBy) {
		this.location = Objects.requireNonNull(location, "Facility location must not null ");
		recordChangeBy(modifiedBy);
	}

	public void updateContactPhone(PhoneNumber contactPhone, UUID modifiedBy) {
		this.contactPhone = Objects.requireNonNull(contactPhone, "Contact phone must not be null");
		recordChangeBy(modifiedBy);
	}

	public void updateContactEmail(Email email, UUID modifiedBy) {
		this.contactEmail = contactEmail;// nullable - optional field , correctable
		recordChangeBy(modifiedBy);
	}

	public void reclassify(FacilityType type, UUID modifiedBy) {
		this.type = Objects.requireNonNull(type, "Facility type must not be null");
		recordChangeBy(modifiedBy);
	}

	public void activate(UUID modifiedBy) {
		changeStatus(FacilityStatus.ACTIVE, modifiedBy);
	}

	public void deactivate(UUID modifiedBy) {
		changeStatus(FacilityStatus.INACTIVE, modifiedBy);
	}

	private void changeStatus(FacilityStatus target, UUID modifiedBy) {
		if (this.status == target) {
			return; // no-op, avoid pointless audit touch
		}
		this.status = target;
		recordChangeBy(modifiedBy);
	}

	// ==========================================================
	// BUSINESS QUERIES
	// ==========================================================

	public boolean isActive() {
		return status == FacilityStatus.ACTIVE;
	}

	// ==========================================================
	// AUDIT
	// ==========================================================

	private void recordChangeBy(UUID modifiedBy) {
		Objects.requireNonNull(modifiedBy, "Modified by (user id) must not be null");
		audit.update(modifiedBy);
	}

	// ==========================================================
	// VALIDATION HELPERS
	// ==========================================================

	private static String requireText(String value, String fieldName) {
		Objects.requireNonNull(value, fieldName + " must not be null");
		String trimmed = value.trim();
		if (trimmed.isEmpty()) {
			throw new IllegalArgumentException(fieldName + " must not be blank");
		}
		return trimmed;
	}

	// ==========================================================
	// ACCESSORS
	// ==========================================================
	public FacilityId facilityId() { return getId(); }
    public FacilityCode code() { return code; }
    public String name() { return name; }
    public FacilityType type() { return type; }
    public Address location() { return location; }
    public PhoneNumber contactPhone() { return contactPhone; }
    public Email contactEmail() { return contactEmail; }
    public FacilityStatus status() { return status; }
    public AuditMetadata audit() { return audit; }

	// ==========================================================
	// IDENTITY
	// ==========================================================

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Facility other))
			return false;
		return Objects.equals(getId(), other.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Facility{id=%s, code=%s, name=%s, status=%s}".formatted(getId(), code, name, status);
	}

}
