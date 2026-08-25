package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

public record SessionResponse(
		String sessionId,
		String issuedAt,
		String expiredAt,
		String userAgent,
		boolean revoked,
		boolean valid
		) {

}
