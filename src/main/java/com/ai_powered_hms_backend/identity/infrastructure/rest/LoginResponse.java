package com.ai_powered_hms_backend.identity.infrastructure.rest;

//login response dto
public record LoginResponse(
		String accessToken,
		String accessTokenExpiresAt,
		String refreshToken, 
		String refreshTokenExpiresAt, 
		String staffId,
		String fullName,
		String role,
		boolean mustChangePassword
		) {

}
