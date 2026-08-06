package com.ai_powered_hms_backend.identity.infrastructure.rest;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//credential creation request dto
public record CreateCredentialRequest(
		@NotNull UUID staffId, 
		@NotBlank @Email String loginEmail,
		@NotBlank String temporaryPassword
		) {

}
