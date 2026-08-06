package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import java.time.LocalDate;
import java.util.UUID;

import com.ai_powered_hms_backend.patient.domain.enums.Genotype;
import com.ai_powered_hms_backend.patient.domain.enums.PatientStatus;
import com.ai_powered_hms_backend.patient.domain.enums.PreferredLanguage;
import com.ai_powered_hms_backend.patient.domain.enums.Religion;
import com.ai_powered_hms_backend.patient.domain.valueobjects.DateOfBirth;
import com.ai_powered_hms_backend.shared_kernel.enums.BloodGroup;
import com.ai_powered_hms_backend.shared_kernel.enums.Gender;
import com.ai_powered_hms_backend.shared_kernel.enums.MaritalStatus;
import com.ai_powered_hms_backend.shared_kernel.enums.PatientType;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AddressEmbeddable;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataEmbeddable;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.EmailConverter;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.PersonNameEmbeddable;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.PhoneNumberConverter;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patients")
public class PatientJpaEntity {

	@Id
	private UUID id;
	
	// ---- MRN (facility code + value, flattened as two columns) ----
    @Column(name = "mrn_facility_code", nullable = false, length = 10)
    private String mrnFacilityCode;
    
    @Column(name = "mrn_value",nullable = false,length = 20)
    private String mrnValue;
    
    // ---- Personal details (flattened — PersonalDetails is a non-persisted grouping) ----
    @Embedded
    private PersonNameEmbeddable fullName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 20)
    private Gender gender;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false, length = 20)
    private MaritalStatus maritalStatus;

    @Convert(converter = DateOfBirthConverter.class)
    @Column(name = "date_of_birth", nullable = false)
    private DateOfBirth dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "religion", nullable = false, length = 30)
    private Religion religion;

    @Column(name = "nationality", nullable = false, length = 100)
    private String nationality;

    @Column(name = "ethnicity", nullable = false, length = 100)
    private String ethnicity;

    @Column(name = "occupation", nullable = false, length = 100)
    private String occupation;
    
 // ---- Medical details (flattened) ----
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group", nullable = false, length = 10)
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "genotype", nullable = false, length = 10)
    private Genotype genotype;

    @Embedded
    private NationalIdEmbeddable nationalId; // optional

    // ---- Contact details (flattened) ----
    
    @Embedded
    @AttributeOverrides({
    			@AttributeOverride(name = "line1", column = @Column(name="home_address_line1")),
        		@AttributeOverride(name = "line2", column = @Column(name="home_address_line2")),
        		@AttributeOverride(name = "city", column = @Column(name="home_city")),
        		@AttributeOverride(name = "state", column = @Column(name="home_state")),
        		@AttributeOverride(name = "postalCode", column = @Column(name="home_postal_code")),
        		@AttributeOverride(name = "country", column = @Column(name="home_country")),
    		})
    private AddressEmbeddable homeAddress;
    
    @Convert(converter = PhoneNumberConverter.class)
    @Column(name = "phone_number", nullable = false,length = 20)
    private PhoneNumber phoneNumber;
    
    @Convert(converter = PhoneNumberConverter.class)
    @Column(name = "alternate_number",length = 20)
    private PhoneNumber alternatePhone;//optional
    
    @Convert(converter = EmailConverter.class)
    @Column(name = "email",length = 150)
    private Email email;
    
    // ---- Relationship info ----
    @Embedded
    private NextOfKinEmbeddable nextOfKin;

    @Embedded
    private InsuranceInformationEmbeddable insuranceInformation; // optional

    // ---- Patient state ----
    @Enumerated(EnumType.STRING)
    @Column(name = "patient_type", nullable = false, length = 20)
    private PatientType patientType;

    @Enumerated(EnumType.STRING)
    @Column(name = "patient_status", nullable = false, length = 20)
    private PatientStatus patientStatus;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_language", nullable = false, length = 30)
    private PreferredLanguage preferredLanguage;

    // ---- Consent ----
    @Embedded
    private ConsentInformationEmbeddable consentInformation;

    // ---- Audit ----
    @Embedded
    private AuditMetadataEmbeddable audit;
    
    
    protected PatientJpaEntity() {
    	//needed by JPA
    }
    
    //all argument constructor
    public PatientJpaEntity(
		 UUID id, String mrnFacilityCode, String mrnValue,
         PersonNameEmbeddable fullName, Gender gender, MaritalStatus maritalStatus,
         DateOfBirth dateOfBirth, Religion religion, String nationality, String ethnicity, String occupation,
         BloodGroup bloodGroup, Genotype genotype, NationalIdEmbeddable nationalId,
         AddressEmbeddable homeAddress, PhoneNumber phoneNumber, PhoneNumber alternatePhone, Email email,
         NextOfKinEmbeddable nextOfKin, InsuranceInformationEmbeddable insuranceInformation,
         PatientType patientType, PatientStatus patientStatus, LocalDate registrationDate,
         PreferredLanguage preferredLanguage, ConsentInformationEmbeddable consentInformation,
         AuditMetadataEmbeddable audit
    	
    ) {
    	this.id = id;
        this.mrnFacilityCode = mrnFacilityCode;
        this.mrnValue = mrnValue;
        this.fullName = fullName;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.dateOfBirth = dateOfBirth;
        this.religion = religion;
        this.nationality = nationality;
        this.ethnicity = ethnicity;
        this.occupation = occupation;
        this.bloodGroup = bloodGroup;
        this.genotype = genotype;
        this.nationalId = nationalId;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.alternatePhone = alternatePhone;
        this.email = email;
        this.nextOfKin = nextOfKin;
        this.insuranceInformation = insuranceInformation;
        this.patientType = patientType;
        this.patientStatus = patientStatus;
        this.registrationDate = registrationDate;
        this.preferredLanguage = preferredLanguage;
        this.consentInformation = consentInformation;
        this.audit = audit;
    }

	public UUID getId() {
		return id;
	}

	public String getMrnFacilityCode() {
		return mrnFacilityCode;
	}

	public String getMrnValue() {
		return mrnValue;
	}

	public PersonNameEmbeddable getFullName() {
		return fullName;
	}

	public Gender getGender() {
		return gender;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public DateOfBirth getDateOfBirth() {
		return dateOfBirth;
	}

	public Religion getReligion() {
		return religion;
	}

	public String getNationality() {
		return nationality;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public String getOccupation() {
		return occupation;
	}

	public BloodGroup getBloodGroup() {
		return bloodGroup;
	}

	public Genotype getGenotype() {
		return genotype;
	}

	public NationalIdEmbeddable getNationalId() {
		return nationalId;
	}

	public AddressEmbeddable getHomeAddress() {
		return homeAddress;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public PhoneNumber getAlternatePhone() {
		return alternatePhone;
	}

	public Email getEmail() {
		return email;
	}

	public NextOfKinEmbeddable getNextOfKin() {
		return nextOfKin;
	}

	public InsuranceInformationEmbeddable getInsuranceInformation() {
		return insuranceInformation;
	}

	public PatientType getPatientType() {
		return patientType;
	}

	public PatientStatus getPatientStatus() {
		return patientStatus;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public PreferredLanguage getPreferredLanguage() {
		return preferredLanguage;
	}

	public ConsentInformationEmbeddable getConsentInformation() {
		return consentInformation;
	}

	public AuditMetadataEmbeddable getAudit() {
		return audit;
	}
    
    
    
}
