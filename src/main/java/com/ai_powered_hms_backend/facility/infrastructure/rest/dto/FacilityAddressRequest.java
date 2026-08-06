package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

// request DTO for address
public record FacilityAddressRequest(
		@NotBlank String line1,
		String line2,
		@NotBlank String city,
		@NotBlank String stateOrRegion,
		@NotBlank String country,
		String postalCode
		
		) {

}
