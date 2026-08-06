package com.ai_powered_hms_backend.patient.infrastructure.rest.dto;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

//main request dto
public record RegisterPatientRequest(
		@Valid @NotNull PersonalDetailsRequest personalDetails,
		@Valid @NotNull MedicalDetailsRequest medicalDetails,
		@Valid @NotNull ContactDetailsRequest contactDetails,
		@Valid @NotNull NextOfKinRequest nextOfKin,
		@Valid InsuranceInformationRequest insuranceInformation, //optional
		@NotNull String patientType,
		@NotNull String preferredLanguage,
		@NotNull UUID facilityId
		
		) {

}
