package com.ai_powered_hms_backend.identity.infrastructure.rest;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
		@NotBlank
		String refreshToken
		) {

}
