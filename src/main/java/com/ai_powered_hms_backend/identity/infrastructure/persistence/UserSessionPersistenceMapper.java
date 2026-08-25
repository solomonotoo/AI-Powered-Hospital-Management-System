package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import com.ai_powered_hms_backend.identity.domain.model.UserSession;
import com.ai_powered_hms_backend.shared_kernel.ids.SessionId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public class UserSessionPersistenceMapper {

	public static UserSessionJpaEntity toEntity(UserSession session) {
		return new UserSessionJpaEntity(
				session.sessionId().value(),
				session.staffId().value(),
				session.issuedAt(),
				session.expiresAt(),
				session.userAgent(),
				session.isRevoked()
				);
	}
	
	public static UserSession toDomain(UserSessionJpaEntity entity) {
		return  UserSession.reconstitute(
				SessionId.of(entity.getId()),
				StaffId.of(entity.getStaffId()),
				entity.getIssuedAt(),
				entity.getExpiresAt(),
				entity.getUserAgent(),
				entity.isRevoked()
				
				);
	}
	
}
