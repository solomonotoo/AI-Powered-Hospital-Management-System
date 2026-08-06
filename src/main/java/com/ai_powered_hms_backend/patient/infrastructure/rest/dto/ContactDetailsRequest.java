package com.ai_powered_hms_backend.patient.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContactDetailsRequest(
		@NotNull AddressRequest homeAddress,
		@NotBlank String phoneNumber,
		String alternatePhone, //optional
		@Email String email //optional
		) {

}
