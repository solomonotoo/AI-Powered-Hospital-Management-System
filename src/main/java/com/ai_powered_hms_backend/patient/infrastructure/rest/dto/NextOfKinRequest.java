package com.ai_powered_hms_backend.patient.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NextOfKinRequest(
		@NotBlank String fullName,
		@NotNull String relationship,
		@NotBlank String phoneNumber,
		AddressRequest address// optional - matches confirmed NextOfKin.address nullability
		) {

}
