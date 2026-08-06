//package com.ai_powered_hms_backend;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.lenient;
//
//import java.time.LocalDate;
//import java.util.UUID;
//
//import org.junit.jupiter.api.Test;
//
//import com.ai_powered_hms_backend.patient.domain.enums.Genotype;
//import com.ai_powered_hms_backend.patient.domain.enums.PatientStatus;
//import com.ai_powered_hms_backend.patient.domain.enums.PreferredLanguage;
//import com.ai_powered_hms_backend.patient.domain.enums.Relationship;
//import com.ai_powered_hms_backend.patient.domain.enums.Religion;
//import com.ai_powered_hms_backend.patient.domain.model.Patient;
//import com.ai_powered_hms_backend.patient.domain.valueobjects.ContactDetails;
//import com.ai_powered_hms_backend.patient.domain.valueobjects.DateOfBirth;
//import com.ai_powered_hms_backend.patient.domain.valueobjects.MedicalDetails;
//import com.ai_powered_hms_backend.patient.domain.valueobjects.NextOfKin;
//import com.ai_powered_hms_backend.patient.domain.valueobjects.PersonalDetails;
//import com.ai_powered_hms_backend.shared_kernel.enums.BloodGroup;
//import com.ai_powered_hms_backend.shared_kernel.enums.Gender;
//import com.ai_powered_hms_backend.shared_kernel.enums.MaritalStatus;
//import com.ai_powered_hms_backend.shared_kernel.enums.PatientType;
//import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
//import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
//import com.ai_powered_hms_backend.shared_kernel.valueobjects.MRN;
//import com.ai_powered_hms_backend.shared_kernel.valueobjects.PersonName;
//import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;
//
//import lombok.experimental.var;
//
//public class PatientDomainModelAggregateTest {
//
//	private PersonalDetails validPersonalDetails() {
//		return new PersonalDetails(
//				
//				new PersonName("Ama","Mensah"),
//				Gender.FEMALE,
//				MaritalStatus.SINGLE,
//				DateOfBirth.of(LocalDate.of(1995, 4, 12)),
//				Religion.CHRISTIANITY,
//				"Ghanaian","Akan","Teacher"
//				);
//	}
//	
//	
//	private MedicalDetails validMedicalDetails() {
//		return new MedicalDetails(BloodGroup.A_NEGATIVE, Genotype.AA, null);
//	}
//	
//	private ContactDetails validContactDetails() {
//		return new ContactDetails(
//				new Address("12 Ring Rd", null, "Accra", "Greater Accra", "00233", "Ghana"),
//				new PhoneNumber("+233098765"),
//				null,
//				null
//				
//				);
//	}
//	
//	private NextOfKin validNextOfKin() {
//		return new NextOfKin("Abena Yeboah", Relationship.SIBLING, new PhoneNumber("+233988764"), null);
//	}
//	
//	
//	@Test
//	void registerPatientWithMandatoryFieldOnly() {
//		MRN mrn = new MRN("KBTH","00001");
//		UUID registeredBy = UUID.randomUUID();
//		
//		Patient patient = Patient.register(
//				validPersonalDetails(),
//				validMedicalDetails(),
//				validContactDetails(),
//				validNextOfKin(),
//				null,//insurance optional
//				PatientType.OUTPATIENT,
//				PreferredLanguage.ENGLISH,
//				mrn,
//				registeredBy
//				
//				);
//		
//		assertThat(patient.patientId()).isNotNull();
//		assertThat(patient.medicalRecordNumber()).isEqualTo(mrn);
//		assertThat(patient.patientStatus()).isEqualTo(PatientStatus.ACTIVE);
//		assertThat(patient.insuranceInformation()).isNull();
//		assertThat(patient.audit().getCreatedBy()).isEqualTo(registeredBy);
//	}
//	
//	@Test
//	void rejectsRegistrationWhenMrnIsNull() {
//		assertThatThrownBy(() -> Patient.register(validPersonalDetails(), 
//				validMedicalDetails(), validContactDetails(), validNextOfKin(),
//				null, PatientType.OUTPATIENT, PreferredLanguage.ENGLISH, 
//				null, UUID.randomUUID())
//				
//			).isInstanceOf(NullPointerException.class)
//		.hasMessageContaining("Medical record number");
//	}
//	
//	@Test
//	void statusTransitionIsIdempotendAndDoesNotDoubleTouchAudit() {
//		Patient patient = Patient.register(validPersonalDetails(), validMedicalDetails(), validContactDetails(),
//                validNextOfKin(), null, PatientType.OUTPATIENT, PreferredLanguage.ENGLISH,
//                new MRN("KBTH", "000002"), UUID.randomUUID());
//		UUID modifier = UUID.randomUUID();
//		patient.deactivate(modifier);
//		var firstUpdate = patient.audit().getUpdatedAt();
//		
//		patient.deactivate(modifier); // same status again — should be a no-op
//		
//		assertThat(patient.patientStatus()).isEqualTo(PatientStatus.INACTIVE);
//		assertThat(patient.audit().getUpdatedAt()).isEqualTo(firstUpdate);
//	}
//	
//	@Test
//	void allowClearingOptionalEmailToNull() {
//		Patient patient = Patient.register(validPersonalDetails(),
//				validMedicalDetails(), 
//				validContactDetails(), 
//				validNextOfKin(), null, 
//				PatientType.OUTPATIENT, 
//				PreferredLanguage.ENGLISH,
//				new MRN("KBTH", "000003"), 
//				UUID.randomUUID());
//		 patient.updateEmail(new Email("ama@example.com"), UUID.randomUUID());
//	        assertThat(patient.email()).isNotNull();
//
//	        patient.updateEmail(null, UUID.randomUUID());
//	        assertThat(patient.email()).isNull();
//	}
//	
//}
