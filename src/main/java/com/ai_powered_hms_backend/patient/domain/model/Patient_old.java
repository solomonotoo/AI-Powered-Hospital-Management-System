//package com.hms_application.patient.domain.model;
//
//import java.time.LocalDate;
//import java.util.Objects;
//import java.util.UUID;
//
//import com.hms_application.patient.domain.enums.Genotype;
//import com.hms_application.patient.domain.enums.PatientStatus;
//import com.hms_application.patient.domain.enums.PreferredLanguage;
//import com.hms_application.patient.domain.enums.Religion;
//import com.hms_application.patient.domain.valueobjects.AuditMetadata;
//import com.hms_application.patient.domain.valueobjects.ConsentInformation;
//import com.hms_application.patient.domain.valueobjects.DateOfBirth;
//import com.hms_application.patient.domain.valueobjects.InsuranceInformation;
//import com.hms_application.patient.domain.valueobjects.NationalId;
//import com.hms_application.patient.domain.valueobjects.NextOfKin;
//import com.hms_application.shared_kernel.base.AggregateRoot;
//import com.hms_application.shared_kernel.enums.BloodGroup;
//import com.hms_application.shared_kernel.enums.Gender;
//import com.hms_application.shared_kernel.enums.MaritalStatus;
//import com.hms_application.shared_kernel.enums.PatientType;
//import com.hms_application.shared_kernel.ids.PatientId;
//import com.hms_application.shared_kernel.valueobjects.Address;
//import com.hms_application.shared_kernel.valueobjects.Email;
//import com.hms_application.shared_kernel.valueobjects.MRN;
//import com.hms_application.shared_kernel.valueobjects.PersonName;
//import com.hms_application.shared_kernel.valueobjects.PhoneNumber;
//
///**
// * Patient Aggregate Root.
// *
// * This is a pure domain object.
// *
// * It intentionally contains:
// *
// * - No Spring annotations - No JPA annotations - No Hibernate annotations - No
// * persistence concerns
// *
// * The Patient aggregate owns patient-related business invariants and state
// * transitions.
// */
//public final class Patient extends AggregateRoot<PatientId> {
//
//	// ==========================================================
//	// PATIENT PERSONAL INFORMATION
//	// ==========================================================
//
//	private PersonName fullName;
//
//	private Gender gender;
//
//	private MaritalStatus maritalStatus;
//
//	private DateOfBirth dateOfBirth;
//
//	private Religion religion;
//
//	private String nationality;
//
//	private String ethnicity;
//
//	private String occupation;
//
//	// ==========================================================
//	// MEDICAL INFORMATION
//	// ==========================================================
//
//	private BloodGroup bloodGroup;
//
//	private Genotype genotype;
//
//	private MRN medicalRecordNumber;
//
//	private NationalId nationalId;
//
//	// ==========================================================
//	// CONTACT INFORMATION
//	// ==========================================================
//
//	private Address homeAddress;
//
//	private PhoneNumber phoneNumber;
//
//	private PhoneNumber alternatePhone;
//
//	private Email email;
//
//	// ==========================================================
//	// RELATIONSHIP INFORMATION
//	// ==========================================================
//
//	private NextOfKin nextOfKin;
//
//	private InsuranceInformation insuranceInformation;
//
//	// ==========================================================
//	// PATIENT STATE
//	// ==========================================================
//
//	private PatientType patientType;
//
//	private PatientStatus patientStatus;
//
//	private LocalDate registrationDate;
//
//	private PreferredLanguage preferredLanguage;
//
//	// ==========================================================
//	// CONSENT
//	// ==========================================================
//
//	private ConsentInformation consentInformation;
//
//	// ==========================================================
//	// AUDIT
//	// ==========================================================
//
//	private AuditMetadata audit;
//
//	// ==========================================================
//	// CONSTRUCTOR FOR NEW PATIENT REGISTRATION
//	// ==========================================================
//
//	private Patient(PatientId id, PersonName fullName, Gender gender, DateOfBirth dateOfBirth, PatientType patientType,
//			UUID registeredBy) {
//
//		super(id);
//
//		this.fullName = Objects.requireNonNull(fullName, "Full name is required");
//
//		this.gender = Objects.requireNonNull(gender, "Gender is required");
//
//		this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Date of birth is required");
//
//		this.patientType = Objects.requireNonNull(patientType, "Patient type is required");
//
//		Objects.requireNonNull(registeredBy, "Registered by is required");
//
//		this.patientStatus = PatientStatus.ACTIVE;
//
//		this.registrationDate = LocalDate.now();
//
//		this.consentInformation = ConsentInformation.empty();
//
//		this.audit = AuditMetadata.create(registeredBy);
//	}
//
//	// ==========================================================
//	// CONSTRUCTOR FOR RECONSTITUTING EXISTING PATIENT
//	// ==========================================================
//
//	private Patient(PatientId id, PersonName fullName, Gender gender, MaritalStatus maritalStatus,
//			DateOfBirth dateOfBirth, Religion religion, String nationality, String ethnicity, String occupation,
//			BloodGroup bloodGroup, Genotype genotype, MRN medicalRecordNumber, NationalId nationalId,
//			Address homeAddress, PhoneNumber phoneNumber, PhoneNumber alternatePhone, Email email, NextOfKin nextOfKin,
//			InsuranceInformation insuranceInformation, PatientType patientType, PatientStatus patientStatus,
//			LocalDate registrationDate, PreferredLanguage preferredLanguage, ConsentInformation consentInformation,
//			AuditMetadata audit) {
//
//		super(id);
//
//		this.fullName = Objects.requireNonNull(fullName, "Full name is required");
//
//		this.gender = Objects.requireNonNull(gender, "Gender is required");
//
//		this.maritalStatus = maritalStatus;
//
//		this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Date of birth is required");
//
//		this.religion = religion;
//
//		this.nationality = nationality;
//
//		this.ethnicity = ethnicity;
//
//		this.occupation = occupation;
//
//		this.bloodGroup = bloodGroup;
//
//		this.genotype = genotype;
//
//		this.medicalRecordNumber = Objects.requireNonNull(medicalRecordNumber, "Medical record number is required");
//
//		this.nationalId = nationalId;
//
//		this.homeAddress = homeAddress;
//
//		this.phoneNumber = phoneNumber;
//
//		this.alternatePhone = alternatePhone;
//
//		this.email = email;
//
//		this.nextOfKin = nextOfKin;
//
//		this.insuranceInformation = insuranceInformation;
//
//		this.patientType = Objects.requireNonNull(patientType, "Patient type is required");
//		
//		this.patientStatus = Objects.requireNonNull(patientStatus, "Patient status is required");
//
//		this.registrationDate = Objects.requireNonNull(registrationDate, "Registration date is required");
//
//		this.preferredLanguage = preferredLanguage;
//
//		this.consentInformation = Objects.requireNonNull(consentInformation, "Consent information is required");
//
//		this.audit = audit;
//	}
//
//	// ==========================================================
//	// REGISTER NEW PATIENT
//	// ==========================================================
//
//	public static Patient register(PersonName fullName, Gender gender, DateOfBirth dateOfBirth, PatientType patientType,
//			MRN medicalRecordNumber, UUID registeredBy) {
//
//		return new Patient(PatientId.newId(), fullName, gender, dateOfBirth, patientType, registeredBy);
//	}
//
//	// ==========================================================
//	// RECONSTITUTE EXISTING PATIENT
//	// ==========================================================
//
//	public static Patient reconstitute(PatientId id, PersonName fullName, Gender gender, MaritalStatus maritalStatus,
//			DateOfBirth dateOfBirth, Religion religion, String nationality, String ethnicity, String occupation,
//			BloodGroup bloodGroup, Genotype genotype, MRN medicalRecordNumber, NationalId nationalId,
//			Address homeAddress, PhoneNumber phoneNumber, PhoneNumber alternatePhone, Email email, NextOfKin nextOfKin,
//			InsuranceInformation insuranceInformation, PatientType patientType, PatientStatus patientStatus,
//			LocalDate registrationDate, PreferredLanguage preferredLanguage, ConsentInformation consentInformation,
//			AuditMetadata audit) {
//
//		return new Patient(id, fullName, gender, maritalStatus, dateOfBirth, religion, nationality, ethnicity,
//				occupation, bloodGroup, genotype, medicalRecordNumber, nationalId, homeAddress, phoneNumber,
//				alternatePhone, email, nextOfKin, insuranceInformation, patientType, patientStatus, registrationDate,
//				preferredLanguage, consentInformation, audit);
//	}
//
//	// ==========================================================
//	// PATIENT INFORMATION COMMANDS
//	// ==========================================================
//
//	public void updateFullName(PersonName fullName, UUID value) {
//
//		this.fullName = Objects.requireNonNull(fullName, "Full name must not be null");
//
//		UserId(value);
//	}
//
//	public void updateGender(Gender gender, UUID value) {
//
//		this.gender = Objects.requireNonNull(gender, "Gender must not be null");
//
//		UserId(value);
//	}
//
//	public void updateMaritalStatus(MaritalStatus maritalStatus, UUID value) {
//
//		this.maritalStatus = Objects.requireNonNull(maritalStatus, "Marital status must not be null");
//
//		UserId(value);
//	}
//
//	public void updateReligion(Religion religion, UUID value) {
//
//		this.religion = Objects.requireNonNull(religion, "Religion must not be null");
//
//		UserId(value);
//	}
//
//	public void updateNationality(String nationality, UUID value) {
//
//		this.nationality = requireText(nationality, "Nationality");
//
//		UserId(value);
//	}
//
//	public void updateEthnicity(String ethnicity, UUID value) {
//
//		this.ethnicity = requireText(ethnicity, "Ethnicity");
//
//		UserId(value);
//	}
//
//	public void updateOccupation(String occupation, UUID value) {
//
//		this.occupation = requireText(occupation, "Occupation");
//
//		UserId(value);
//	}
//
//	// ==========================================================
//	// MEDICAL INFORMATION COMMANDS
//	// ==========================================================
//
//	public void updateBloodGroup(BloodGroup bloodGroup, UUID value) {
//
//		this.bloodGroup = Objects.requireNonNull(bloodGroup, "Blood group must not be null");
//
//		UserId(value);
//	}
//
//	public void updateGenotype(Genotype genotype, UUID value) {
//
//		this.genotype = Objects.requireNonNull(genotype, "Genotype must not be null");
//
//		UserId(value);
//	}
//
//	public void updateMedicalRecordNumber(MRN medicalRecordNumber, UUID value) {
//
//		this.medicalRecordNumber = Objects.requireNonNull(medicalRecordNumber,
//				"Medical record number must not be null");
//
//		UserId(value);
//	}
//
//	public void updateNationalId(NationalId nationalId, UUID value) {
//
//		this.nationalId = Objects.requireNonNull(nationalId, "National ID must not be null");
//
//		UserId(value);
//	}
//
//	// ==========================================================
//	// CONTACT COMMANDS
//	// ==========================================================
//
//	public void relocated(Address newAddress, UUID value) {
//
//		this.homeAddress = Objects.requireNonNull(newAddress, "Address must not be null");
//
//		UserId(value);
//	}
//
//	public void updatePhoneNumber(PhoneNumber phoneNumber, UUID value) {
//
//		this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone number must not be null");
//
//		UserId(value);
//	}
//
//	public void updateAlternatePhone(PhoneNumber alternatePhone, UUID value) {
//
//		this.alternatePhone = Objects.requireNonNull(alternatePhone, "Alternate phone must not be null");
//
//		UserId(value);
//	}
//
//	public void updateEmail(Email email, UUID value) {
//
//		this.email = Objects.requireNonNull(email, "Email must not be null");
//
//		UserId(value);
//	}
//
//	// ==========================================================
//	// RELATIONSHIP COMMANDS
//	// ==========================================================
//
//	public void updateNextOfKin(NextOfKin nextOfKin, UUID value) {
//
//		this.nextOfKin = Objects.requireNonNull(nextOfKin, "Next of kin must not be null");
//
//		UserId(value);
//	}
//
//	public void updateInsuranceInformation(InsuranceInformation insuranceInformation, UUID value) {
//
//		this.insuranceInformation = Objects.requireNonNull(insuranceInformation,
//				"Insurance information must not be null");
//
//		UserId(value);
//	}
//
//	// ==========================================================
//	// PATIENT STATE COMMANDS
//	// ==========================================================
//
//	public void changePatientType(PatientType patientType, UUID value) {
//
//		this.patientType = Objects.requireNonNull(patientType, "Patient type must not be null");
//
//		UserId(value);
//	}
//
//	public void changePreferredLanguage(PreferredLanguage preferredLanguage, UUID value) {
//
//		this.preferredLanguage = Objects.requireNonNull(preferredLanguage, "Preferred language must not be null");
//
//		UserId(value);
//	}
//
//	// ==========================================================
//	// CONSENT COMMANDS
//	// ==========================================================
//
//	public void giveConsentToTreat(UUID value) {
//
//		consentInformation.giveTreatmentConsent(value);
//
//		UserId(value);
//	}
//
//	public void withdrawConsentToTreat(UUID value) {
//
//		consentInformation.withdrawTreatmentConsent(value);
//
//		UserId(value);
//	}
//
//	public void giveConsentToShareData(UUID value) {
//
//		consentInformation.giveDataSharingConsent(value);
//
//		UserId(value);
//	}
//
//	public void withdrawConsentToShareData(UUID value) {
//
//		consentInformation.withdrawDataSharingConsent(value);
//
//		UserId(value);
//	}
//
//	// ==========================================================
//	// AUDIT
//	// ==========================================================
//
//	private void UserId(UUID value) {
//
//		Objects.requireNonNull(value, "User ID must not be null");
//
//		if (audit != null) {
//
//			audit.update(value);
//		}
//	}
//
//	// ==========================================================
//	// VALIDATION HELPERS
//	// ==========================================================
//
//	private static String requireText(String value, String fieldName) {
//
//		Objects.requireNonNull(value, fieldName + " must not be null");
//
//		String trimmed = value.trim();
//
//		if (trimmed.isEmpty()) {
//
//			throw new IllegalArgumentException(fieldName + " must not be blank");
//		}
//
//		return trimmed;
//	}
//
//	// ==========================================================
//	// ACCESSORS
//	// ==========================================================
//
//	public PatientId patientId() {
//
//		return getId();
//	}
//
//	public PersonName fullName() {
//
//		return fullName;
//	}
//
//	public Gender gender() {
//
//		return gender;
//	}
//
//	public MaritalStatus maritalStatus() {
//
//		return maritalStatus;
//	}
//
//	public DateOfBirth dateOfBirth() {
//
//		return dateOfBirth;
//	}
//
//	public Religion religion() {
//
//		return religion;
//	}
//
//	public String nationality() {
//
//		return nationality;
//	}
//
//	public String ethnicity() {
//
//		return ethnicity;
//	}
//
//	public String occupation() {
//
//		return occupation;
//	}
//
//	public BloodGroup bloodGroup() {
//
//		return bloodGroup;
//	}
//
//	public Genotype genotype() {
//
//		return genotype;
//	}
//
//	public MRN medicalRecordNumber() {
//
//		return medicalRecordNumber;
//	}
//
//	public NationalId nationalId() {
//
//		return nationalId;
//	}
//
//	public Address homeAddress() {
//
//		return homeAddress;
//	}
//
//	public PhoneNumber phoneNumber() {
//
//		return phoneNumber;
//	}
//
//	public PhoneNumber alternatePhone() {
//
//		return alternatePhone;
//	}
//
//	public Email email() {
//
//		return email;
//	}
//
//	public NextOfKin nextOfKin() {
//
//		return nextOfKin;
//	}
//
//	public InsuranceInformation insuranceInformation() {
//
//		return insuranceInformation;
//	}
//
//	public PatientType patientType() {
//
//		return patientType;
//	}
//
//	public PatientStatus patientStatus() {
//
//		return patientStatus;
//	}
//
//	public LocalDate registrationDate() {
//
//		return registrationDate;
//	}
//
//	public PreferredLanguage preferredLanguage() {
//
//		return preferredLanguage;
//	}
//
//	public ConsentInformation consentInformation() {
//
//		return consentInformation;
//	}
//
//	public AuditMetadata audit() {
//
//		return audit;
//	}
//}