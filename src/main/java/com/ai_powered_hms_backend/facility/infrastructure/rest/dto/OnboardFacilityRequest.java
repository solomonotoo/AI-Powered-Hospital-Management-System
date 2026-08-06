package com.ai_powered_hms_backend.facility.infrastructure.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


//request DTO
public record OnboardFacilityRequest(
		@NotBlank @Pattern(regexp = "^[A-Za-z0-9]{2,10}$", message = "Facility code must be 2-10 alphanumeric characters")
		String code,
		
		@NotBlank String name,
		@NotNull String type,
		@Valid @NotNull FacilityAddressRequest location,
		
		@NotBlank String contactPhone,
		@Email String contactEmail //optional
		
		
		) {

}
