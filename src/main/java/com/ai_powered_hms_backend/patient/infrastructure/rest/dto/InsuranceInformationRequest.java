package com.ai_powered_hms_backend.patient.infrastructure.rest.dto;

import java.time.LocalDate;
//request dto
public record InsuranceInformationRequest(
		String provider,
		String policyNumber,
		String groupNumber,
		LocalDate coverageStartDate,
		LocalDate expirationDate
		
		) {

}
