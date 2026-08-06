package com.ai_powered_hms_backend.patient.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.ai_powered_hms_backend.patient.domain.enums.Genotype;
import com.ai_powered_hms_backend.patient.domain.enums.PatientStatus;
import com.ai_powered_hms_backend.patient.domain.enums.PreferredLanguage;
import com.ai_powered_hms_backend.patient.domain.enums.Religion;
import com.ai_powered_hms_backend.patient.domain.valueobjects.ConsentInformation;
import com.ai_powered_hms_backend.patient.domain.valueobjects.ContactDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.DateOfBirth;
import com.ai_powered_hms_backend.patient.domain.valueobjects.InsuranceInformation;
import com.ai_powered_hms_backend.patient.domain.valueobjects.MedicalDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.NationalId;
import com.ai_powered_hms_backend.patient.domain.valueobjects.NextOfKin;
import com.ai_powered_hms_backend.patient.domain.valueobjects.PersonalDetails;
import com.ai_powered_hms_backend.shared_kernel.base.AggregateRoot;
import com.ai_powered_hms_backend.shared_kernel.enums.BloodGroup;
import com.ai_powered_hms_backend.shared_kernel.enums.Gender;
import com.ai_powered_hms_backend.shared_kernel.enums.MaritalStatus;
import com.ai_powered_hms_backend.shared_kernel.enums.PatientType;
import com.ai_powered_hms_backend.shared_kernel.ids.PatientId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.AuditMetadata;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.MRN;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PersonName;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

/**
 * Patient Aggregate Root.
 *
 * This is a pure domain object.
 *
 * It intentionally contains:
 *
 * - No Spring annotations - No JPA annotations - No Hibernate annotations - No
 * persistence concerns
 *
 * The Patient aggregate owns patient-related business invariants and state
 * transitions.
 */
public final class Patient extends AggregateRoot<PatientId> {

	// ==========================================================
	// PATIENT PERSONAL INFORMATION
	// ==========================================================

	private PersonName fullName;

	private Gender gender;

	private MaritalStatus maritalStatus;

	private DateOfBirth dateOfBirth;

	private Religion religion;

	private String nationality;

	private String ethnicity;

	private String occupation;

	// ==========================================================
	// MEDICAL INFORMATION
	// ==========================================================

	private BloodGroup bloodGroup;

	private Genotype genotype;

	private MRN medicalRecordNumber;

	private NationalId nationalId;

	// ==========================================================
	// CONTACT INFORMATION
	// ==========================================================

	private Address homeAddress;

	private PhoneNumber phoneNumber;

	private PhoneNumber alternatePhone;

	private Email email;

	// ==========================================================
	// RELATIONSHIP INFORMATION
	// ==========================================================

	private NextOfKin nextOfKin;

	private InsuranceInformation insuranceInformation;

	// ==========================================================
	// PATIENT STATE
	// ==========================================================

	private PatientType patientType;

	private PatientStatus patientStatus;

	private LocalDate registrationDate;

	private PreferredLanguage preferredLanguage;

	// ==========================================================
	// CONSENT
	// ==========================================================

	private ConsentInformation consentInformation;

	// ==========================================================
	// AUDIT
	// ==========================================================

	private AuditMetadata audit;

	// ==========================================================
	// CONSTRUCTOR FOR NEW PATIENT REGISTRATION
	// ==========================================================

	private Patient(PatientId id, PersonalDetails personalDetails, MedicalDetails medicalDetails,
			ContactDetails contactDetails, NextOfKin nextOfKin, InsuranceInformation insuranceInformation,
			PatientType patientType, PreferredLanguage preferredLanguage, MRN medicalRecordNumber, UUID registeredBy) {

		super(id);

		applyPersonalDetails(personalDetails);
		applyMedicalDetails(medicalDetails);
		applyContactDetails(contactDetails);

		this.nextOfKin = Objects.requireNonNull(nextOfKin, "Next of kin is required");
		this.insuranceInformation = insuranceInformation();
		this.patientType = Objects.requireNonNull(patientType, "Patient type is required");
		this.preferredLanguage = Objects.requireNonNull(preferredLanguage, "Preferred language is required");

		this.medicalRecordNumber = Objects.requireNonNull(medicalRecordNumber, "Medical record number is required");

		Objects.requireNonNull(registeredBy, "Registered by is required");
		this.patientStatus = PatientStatus.ACTIVE;
		this.registrationDate = LocalDate.now();
		this.consentInformation = ConsentInformation.empty();
		this.audit = AuditMetadata.create(registeredBy);
	}

	// ==========================================================
	// CONSTRUCTOR FOR RECONSTITUTING EXISTING PATIENT
	// ==========================================================

	private Patient(PatientId id, PersonalDetails personalDetails, MedicalDetails medicalDetails,
			ContactDetails contactDetails, MRN medicalRecordNumber, NextOfKin nextOfKin,
			InsuranceInformation insuranceInformation, PatientType patientType, PatientStatus patientStatus,
			LocalDate registrationDate, PreferredLanguage preferredLanguage, ConsentInformation consentInformation,
			AuditMetadata audit) {

		super(id);

		applyPersonalDetails(personalDetails);
		applyMedicalDetails(medicalDetails);
		applyContactDetails(contactDetails);

		this.medicalRecordNumber = Objects.requireNonNull(medicalRecordNumber, "Medical record number is required");

		this.nextOfKin = Objects.requireNonNull(nextOfKin, "Next of kin is required");

		this.insuranceInformation = insuranceInformation;

		this.patientType = Objects.requireNonNull(patientType, "Patient type is required");

		this.patientStatus = Objects.requireNonNull(patientStatus, "Patient status is required");

		this.registrationDate = Objects.requireNonNull(registrationDate, "Registration date is required");

		this.preferredLanguage = Objects.requireNonNull(preferredLanguage, "Preferred language is required");

		this.consentInformation = Objects.requireNonNull(consentInformation, "Consent information is required");

		this.audit = Objects.requireNonNull(audit, "Audit metadata is required");
	}

	// ==========================================================
	// SHARED FIELD ASSIGNMENT (single source of validation truth)
	// ==========================================================

	private void applyPersonalDetails(PersonalDetails details) {
		Objects.requireNonNull(details, "Personal details are required");
		this.fullName = Objects.requireNonNull(details.fullName(), "Full name is required");
		this.gender = Objects.requireNonNull(details.gender(), "Gender is required");

		this.maritalStatus = Objects.requireNonNull(details.maritalStatus(), "Marital status is required");

		this.dateOfBirth = Objects.requireNonNull(details.dateOfBirth(), "Date of birth is required");

		this.religion = details.religion(); // optional

//		this.nationality = details.nationality();
//		this.ethnicity = details.ethnicity();
//		this.occupation = details.occupation();
		this.nationality = requireText(details.nationality(), "Nationality");

		this.ethnicity = optionalText(details.ethnicity());

		this.occupation = optionalText(details.occupation());

	}

	private void applyMedicalDetails(MedicalDetails details) {
		Objects.requireNonNull(details, "Medical details are required");

//		this.bloodGroup = Objects.requireNonNull(details.bloodGroup(), "Blood group is required");
//
//		this.genotype = Objects.requireNonNull(details.genotype(), "Genotype is required");
//
//		this.nationalId = Objects.requireNonNull(details.nationalId(), "National ID is required");

		this.bloodGroup = details.bloodGroup();

		this.genotype = details.genoType();

		this.nationalId = details.nationalId();
	}

	private void applyContactDetails(ContactDetails details) {
		Objects.requireNonNull(details, "Contact details are required");

		this.homeAddress = Objects.requireNonNull(details.homeAddress(), "Home address is required");

		this.phoneNumber = Objects.requireNonNull(details.phoneNumber(), "Phone number is required");

		this.alternatePhone = details.alternatePhoneNumber(); // optional, nullable

		this.email = details.email();
	}

	// ==========================================================
	// REGISTER NEW PATIENT
	// ==========================================================

	public static Patient register(PersonalDetails personalDetails, MedicalDetails medicalDetails,
			ContactDetails contactDetails, NextOfKin nextOfKin, InsuranceInformation insuranceInformation,
			PatientType patientType, PreferredLanguage preferredLanguage, MRN medicalRecordNumber, UUID registeredBy) {

		return new Patient(PatientId.newId(), personalDetails, medicalDetails, contactDetails, nextOfKin,
				insuranceInformation, patientType, preferredLanguage, medicalRecordNumber, registeredBy);
	}

	// ==========================================================
	// RECONSTITUTE EXISTING PATIENT (used by persistence mapper)
	// ==========================================================

	public static Patient reconstitute(PatientId id, PersonalDetails personalDetails, MedicalDetails medicalDetails,
			ContactDetails contactDetails, MRN medicalRecordNumber, NextOfKin nextOfKin,
			InsuranceInformation insuranceInformation, PatientType patientType, PatientStatus patientStatus,
			LocalDate registrationDate, PreferredLanguage preferredLanguage, ConsentInformation consentInformation,
			AuditMetadata audit) {

		return new Patient(id, // id from database
				personalDetails, medicalDetails, contactDetails, medicalRecordNumber, nextOfKin, insuranceInformation,
				patientType, patientStatus, registrationDate, preferredLanguage, consentInformation, audit

		);
	}

	// ==========================================================
	// PATIENT INFORMATION COMMANDS
	// ==========================================================

	public void updateFullName(PersonName fullName, UUID modifiedBy) {

		this.fullName = Objects.requireNonNull(fullName, "Full name must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updateGender(Gender gender, UUID modifiedBy) {

		this.gender = Objects.requireNonNull(gender, "Gender must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updateMaritalStatus(MaritalStatus maritalStatus, UUID modifiedBy) {

		this.maritalStatus = Objects.requireNonNull(maritalStatus, "Marital status must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updateReligion(Religion religion, UUID modifiedBy) {

		this.religion = Objects.requireNonNull(religion, "Religion must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updateNationality(String nationality, UUID modifiedBy) {

		this.nationality = requireText(nationality, "Nationality");

		recordChangeBy(modifiedBy);
	}

	public void updateEthnicity(String ethnicity, UUID modifiedBy) {

		this.ethnicity = optionalText(ethnicity);

		recordChangeBy(modifiedBy);
	}

	public void updateOccupation(String occupation, UUID modifiedBy) {

		this.occupation = optionalText(occupation);

		recordChangeBy(modifiedBy);
	}

	// ==========================================================
	// MEDICAL INFORMATION COMMANDS
	// ==========================================================

	public void updateBloodGroup(BloodGroup bloodGroup, UUID modifiedBy) {

		this.bloodGroup = Objects.requireNonNull(bloodGroup, "Blood group must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updateBloodGroup(UUID modifiedBy) {
		this.bloodGroup = null;// dropdown field which is not required
		recordChangeBy(modifiedBy);
	}

	public void updateGenotype(Genotype genotype, UUID modifiedBy) {

		this.genotype = Objects.requireNonNull(genotype, "Genotype must not be null");
		recordChangeBy(modifiedBy);
	}

	public void updateGenotype(UUID modifiedBy) {
		this.genotype = null;
		recordChangeBy(modifiedBy);
	}

	public void updateMedicalRecordNumber(MRN medicalRecordNumber, UUID modifiedBy) {

		this.medicalRecordNumber = Objects.requireNonNull(medicalRecordNumber,
				"Medical record number must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updateNationalId(NationalId nationalId, UUID modifiedBy) {

		this.nationalId = nationalId;

		recordChangeBy(modifiedBy);
	}

	// ==========================================================
	// CONTACT COMMANDS
	// ==========================================================

	public void relocated(Address newAddress, UUID modifiedBy) {

		this.homeAddress = Objects.requireNonNull(newAddress, "Address must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updatePhoneNumber(PhoneNumber phoneNumber, UUID modifiedBy) {

		this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone number must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updateAlternatePhone(PhoneNumber alternatePhone, UUID modifiedBy) {
		// Nullable by design — allow clearing it by passing null explicitly
		// via a separate clearAlternatePhone() if you want that to be intentional
		// rather than silent. For now this mirrors original behavior.

		this.alternatePhone = alternatePhone;

		recordChangeBy(modifiedBy);
	}

	public void updateEmail(Email email, UUID modifiedBy) {

//		this.email = Objects.requireNonNull(email, "Email must not be null");

		recordChangeBy(modifiedBy);
	}

	// ==========================================================
	// RELATIONSHIP COMMANDS
	// ==========================================================

	public void updateNextOfKin(NextOfKin nextOfKin, UUID modifiedBy) {

		this.nextOfKin = Objects.requireNonNull(nextOfKin, "Next of kin must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updateInsuranceInformation(InsuranceInformation insuranceInformation, UUID modifiedBy) {

		this.insuranceInformation = Objects.requireNonNull(insuranceInformation,
				"Insurance information must not be null");

		recordChangeBy(modifiedBy);
	}

	public void updateInsuranceInformation(UUID modifiedBy) {

		this.insuranceInformation = null;

		recordChangeBy(modifiedBy);
	}

	// ==========================================================
	// PATIENT STATE COMMANDS
	// ==========================================================

	public void changePatientType(PatientType patientType, UUID modifiedBy) {

		this.patientType = Objects.requireNonNull(patientType, "Patient type must not be null");

		recordChangeBy(modifiedBy);
	}

	public void changePreferredLanguage(PreferredLanguage preferredLanguage, UUID modifiedBy) {

		this.preferredLanguage = Objects.requireNonNull(preferredLanguage, "Preferred language must not be null");

		recordChangeBy(modifiedBy);
	}

//	 public void deactivate(UUID modifiedBy) {
//		 requireTransitionAllowed(PatientStatus.INACTIVE);
//	        this.patientStatus = PatientStatus.INACTIVE; // adjust to your actual enum values
//	        recordChangeBy(modifiedBy);
//	    }
//
//	    public void reactivate(UUID modifiedBy) {
//	    	 requireTransitionAllowed(PatientStatus.ACTIVE);
//	        this.patientStatus = PatientStatus.ACTIVE;
//	        recordChangeBy(modifiedBy);
//	    }
//	    
//	    public void markDeceased(UUID modifiedBy) {
//	        requireTransitionAllowed(PatientStatus.DECEASED);
//	        this.patientStatus = PatientStatus.DECEASED;
//	        recordChangeBy(modifiedBy);
//	    }
//	    
//	    public void archive(UUID modifiedBy) {
//	        requireTransitionAllowed(PatientStatus.ARCHIVED);
//	        this.patientStatus = PatientStatus.ARCHIVED;
//	        recordChangeBy(modifiedBy);
//	    }

//	    private void requireTransitionAllowed(PatientStatus target) {
//	        boolean allowed = switch (patientStatus) {
//	            case ACTIVE   -> target == PatientStatus.INACTIVE
//	                           || target == PatientStatus.DECEASED
//	                           || target == PatientStatus.ARCHIVED;
//	            case INACTIVE -> target == PatientStatus.ACTIVE
//	                           || target == PatientStatus.DECEASED
//	                           || target == PatientStatus.ARCHIVED;
//	            case DECEASED -> false; // terminal state — no transitions out
//	            case ARCHIVED -> false; // terminal state — must be explicitly un-archived via a separate, audited operation if ever needed
//	        };
//
//	        if (!allowed) {
//	            throw new IllegalStateException(
//	                    "Cannot transition patient from %s to %s".formatted(patientStatus, target)
//	            );
//	        }
//	    }

	public void deactivate(UUID modifiedBy) {
		changeStatus(PatientStatus.INACTIVE, modifiedBy);
	}

	public void reactivate(UUID modifiedBy) {
		changeStatus(PatientStatus.ACTIVE, modifiedBy);
	}

	public void markDeceased(UUID modifiedBy) {
		changeStatus(PatientStatus.DECEASED, modifiedBy);
	}

	public void archive(UUID modifiedBy) {
		changeStatus(PatientStatus.ARCHIVED, modifiedBy);
	}

	private void changeStatus(PatientStatus target, UUID modifiedBy) {
		Objects.requireNonNull(target, "Target status must not be null");

		if (this.patientStatus == target) {
			return; // no-op, avoid a pointless audit touch for setting the same status
		}

		this.patientStatus = target;
		recordChangeBy(modifiedBy);
	}
	
	// ==========================================================
	// CONSENT COMMANDS
	// ==========================================================

	public void giveConsentToTreat(UUID modifiedBy) {

		consentInformation.giveTreatmentConsent(modifiedBy);

		recordChangeBy(modifiedBy);
	}

	public void withdrawConsentToTreat(UUID modifiedBy) {

		consentInformation.withdrawTreatmentConsent(modifiedBy);

		recordChangeBy(modifiedBy);
	}

	public void giveConsentToShareData(UUID modifiedBy) {

		consentInformation.giveDataSharingConsent(modifiedBy);

		recordChangeBy(modifiedBy);
	}

	public void withdrawConsentToShareData(UUID modifiedBy) {

		consentInformation.withdrawDataSharingConsent(modifiedBy);

		recordChangeBy(modifiedBy);
	}

	// ==========================================================
	// AUDIT
	// ==========================================================

	private void recordChangeBy(UUID modifiedBy) {

		Objects.requireNonNull(modifiedBy, "Modified by (user id) must not be null");
		audit.update(modifiedBy); // audit is always non-null post-construction now

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

	private static String optionalText(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}

		return value.trim();
	}

	// ==========================================================
	// ACCESSORS
	// ==========================================================

	public PatientId patientId() {

		return getId();
	}

	public PersonName fullName() {

		return fullName;
	}

	public Gender gender() {

		return gender;
	}

	public MaritalStatus maritalStatus() {

		return maritalStatus;
	}

	public DateOfBirth dateOfBirth() {

		return dateOfBirth;
	}

	public Religion religion() {

		return religion;
	}

	public String nationality() {

		return nationality;
	}

	public String ethnicity() {

		return ethnicity;
	}

	public String occupation() {

		return occupation;
	}

	public BloodGroup bloodGroup() {

		return bloodGroup;
	}

	public Genotype genotype() {

		return genotype;
	}

	public MRN medicalRecordNumber() {

		return medicalRecordNumber;
	}

	public NationalId nationalId() {

		return nationalId;
	}

	public Address homeAddress() {

		return homeAddress;
	}

	public PhoneNumber phoneNumber() {

		return phoneNumber;
	}

	public PhoneNumber alternatePhone() {

		return alternatePhone;
	}

	public Email email() {

		return email;
	}

	public NextOfKin nextOfKin() {

		return nextOfKin;
	}

	public InsuranceInformation insuranceInformation() {

		return insuranceInformation;
	}

	public PatientType patientType() {

		return patientType;
	}

	public PatientStatus patientStatus() {

		return patientStatus;
	}

	public LocalDate registrationDate() {

		return registrationDate;
	}

	public PreferredLanguage preferredLanguage() {

		return preferredLanguage;
	}

	public ConsentInformation consentInformation() {

		return consentInformation;
	}

	public AuditMetadata audit() {

		return audit;
	}

	// ==========================================================
	// IDENTITY — remove if AggregateRoot<PatientId> already implements this
	// ==========================================================

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Patient other))
			return false;
		return Objects.equals(getId(), other.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Patient{id=%s, mrn=%s, status=%s}".formatted(getId(), medicalRecordNumber, patientStatus);
	}
}