package com.ai_powered_hms_backend.patient.application.command;

import java.util.UUID;

import com.ai_powered_hms_backend.patient.domain.enums.PreferredLanguage;
import com.ai_powered_hms_backend.patient.domain.valueobjects.ContactDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.InsuranceInformation;
import com.ai_powered_hms_backend.patient.domain.valueobjects.MedicalDetails;
import com.ai_powered_hms_backend.patient.domain.valueobjects.NextOfKin;
import com.ai_powered_hms_backend.patient.domain.valueobjects.PersonalDetails;
import com.ai_powered_hms_backend.shared_kernel.enums.PatientType;

//NB this is CQRS actual command, not REST DTO
public record RegisterPatientCommand(
		PersonalDetails personalDetails,
		MedicalDetails medicalDetails,
		ContactDetails contactDetails,
		NextOfKin nextOfKin,
		InsuranceInformation insuranceInformation,
		PatientType patientType,
		PreferredLanguage preferredLanguage,
		UUID facilityId,
		UUID registeredBy
		
		) {

}
