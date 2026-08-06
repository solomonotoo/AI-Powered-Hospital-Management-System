package com.ai_powered_hms_backend.identity.infrastructure.rest;


//login response dto
public record LoginResponse(
		String token,
		String expiresAt,
		String staffId,
		String fullName,
		String role,
		boolean mustChangePassword
		) {

}
