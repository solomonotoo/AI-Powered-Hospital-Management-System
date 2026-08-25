package com.ai_powered_hms_backend.identity.infrastructure.rest.mapper;

import com.ai_powered_hms_backend.identity.domain.model.UserSession;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.SessionResponse;

public class SessionResponseMapper {

	public static SessionResponse toResponse(UserSession session) {
		return new SessionResponse(
				session.sessionId().value().toString(),
				session.issuedAt().toString(),
				session.expiresAt().toString(),
				session.userAgent(),
				session.isRevoked(),
				session.isValid()
				
				);
	}
}
