package com.ai_powered_hms_backend.patient.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

//request dto for address
public record AddressRequest(
		@NotBlank String line1,
		String line2,
		@NotBlank String city,
		String state,
		String postalCode,
		@NotBlank String country
		) {

}
