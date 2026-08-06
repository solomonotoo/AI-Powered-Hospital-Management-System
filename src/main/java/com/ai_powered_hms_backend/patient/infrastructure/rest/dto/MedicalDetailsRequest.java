package com.ai_powered_hms_backend.patient.infrastructure.rest.dto;

import jakarta.validation.constraints.NotNull;

//request dto
public record MedicalDetailsRequest(
		@NotNull String bloodGroup,
		@NotNull String genoType,
		String nationalIdType, //optional - null if no national id is provided
		String nationalIdNumber, //optional
		String nationalIdIssuingCountry//optional
		) {

}
