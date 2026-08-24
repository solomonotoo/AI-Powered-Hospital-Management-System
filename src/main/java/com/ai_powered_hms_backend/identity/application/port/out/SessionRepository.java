package com.ai_powered_hms_backend.identity.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ai_powered_hms_backend.identity.domain.model.UserSession;
import com.ai_powered_hms_backend.shared_kernel.ids.SessionId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public interface SessionRepository {
	void save(UserSession session);
	Optional<UserSession> findById(SessionId id);
	List<UserSession> findByStaffId(StaffId staffId);
	
	default boolean isValid(UUID sessionId) {
		return findById(SessionId.of(sessionId))
				.map(UserSession::isValid)
				.orElse(false);
	}
}
