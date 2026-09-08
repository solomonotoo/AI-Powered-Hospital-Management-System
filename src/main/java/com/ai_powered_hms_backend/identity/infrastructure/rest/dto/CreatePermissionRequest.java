package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePermissionRequest(
		@NotBlank String code,
		String description
		) {

}
