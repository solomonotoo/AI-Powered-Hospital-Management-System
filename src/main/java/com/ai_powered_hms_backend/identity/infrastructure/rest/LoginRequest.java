package com.ai_powered_hms_backend.identity.infrastructure.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//Login request dto
public record LoginRequest(
		@NotBlank @Email String email,
		@NotBlank String password
		) {

}
