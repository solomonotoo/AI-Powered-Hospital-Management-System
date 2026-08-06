package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import com.ai_powered_hms_backend.patient.domain.model.Patient;
import com.ai_powered_hms_backend.patient.domain.valueobjects.ContactDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.MedicalDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.PersonalDetails;
import com.ai_powered_hms_backend.shared_kernel.ids.PatientId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AddressMapper;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataMapper;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.PersonNameMapper;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.MRN;

//maps or convert PatientJpaEntity object to Patient domain object and vice versa
public class PatientPersistenceMapper {

	public static PatientJpaEntity toEntity(Patient patient) {
		return new PatientJpaEntity(patient.getId().value(), patient.medicalRecordNumber().facilityCode(),
				patient.medicalRecordNumber().value(), PersonNameMapper.toEmbeddable(patient.fullName()),
				patient.gender(), patient.maritalStatus(), patient.dateOfBirth(), // no conversion needed — converter
																					// handles it
				patient.religion(), patient.nationality(), patient.ethnicity(), patient.occupation(),
				patient.bloodGroup(), patient.genotype(), NationalIdMapper.toEmbeddable(patient.nationalId()),
				AddressMapper.toEmbeddable(patient.homeAddress()), patient.phoneNumber(), // no conversion needed
				patient.alternatePhone(), // no conversion needed
				patient.email(), // no conversion needed
				NextOfKinMapper.toEmbeddable(patient.nextOfKin()),
				InsuranceInformationMapper.toEmbeddable(patient.insuranceInformation()), patient.patientType(),
				patient.patientStatus(), patient.registrationDate(), patient.preferredLanguage(),
				ConsentInformationMapper.toEmbeddable(patient.consentInformation()),
				AuditMetadataMapper.toEmbeddable(patient.audit()));
	}

	public static Patient toDomain(PatientJpaEntity entity) {
		PersonalDetails personalDetails = new PersonalDetails(PersonNameMapper.toDomain(entity.getFullName()),
				entity.getGender(), entity.getMaritalStatus(), entity.getDateOfBirth(), entity.getReligion(),
				entity.getNationality(), entity.getEthnicity(), entity.getOccupation());

		MedicalDetails medicalDetails = new MedicalDetails(entity.getBloodGroup(), entity.getGenotype(),
				NationalIdMapper.toDomain(entity.getNationalId()));

		ContactDetails contactDetails = new ContactDetails(AddressMapper.toDomain(entity.getHomeAddress()),
				entity.getPhoneNumber(), entity.getAlternatePhone(), entity.getEmail());

		MRN mrn = new MRN(entity.getMrnFacilityCode(), entity.getMrnValue());

		return Patient.reconstitute(PatientId.of(entity.getId()), personalDetails, medicalDetails, contactDetails, mrn,
				NextOfKinMapper.toDomain(entity.getNextOfKin()),
				InsuranceInformationMapper.toDomaIn(entity.getInsuranceInformation()), entity.getPatientType(),
				entity.getPatientStatus(), entity.getRegistrationDate(), entity.getPreferredLanguage(),
				ConsentInformationMapper.toDomain(entity.getConsentInformation()),
				AuditMetadataMapper.toDomain(entity.getAudit()));
	}

}
