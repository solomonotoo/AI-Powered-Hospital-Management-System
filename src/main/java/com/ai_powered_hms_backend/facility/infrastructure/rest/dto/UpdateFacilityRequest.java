package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateFacilityRequest(
		@NotBlank String name,
		@NotNull String type,
		@NotNull FacilityAddressRequest location,
		@NotBlank String contactPhone,
		@Email String contactEmail
		) {

}
