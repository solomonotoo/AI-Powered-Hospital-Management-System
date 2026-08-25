package com.ai_powered_hms_backend.identity.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.port.out.SessionRepository;
import com.ai_powered_hms_backend.identity.domain.model.UserSession;
import com.ai_powered_hms_backend.shared_kernel.ids.SessionId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

@Service
public class SessionQueryService {

	private final SessionRepository sessionRepository;

	public SessionQueryService(SessionRepository sessionRepository) {
		super();
		this.sessionRepository = sessionRepository;
	}
	
	@Transactional(readOnly = true)
	public List<UserSession> listForUser(StaffId staffId){
		return sessionRepository.findByStaffId(staffId);
	}
	
	@Transactional
	public void revokeIfOwnedBy(SessionId sessionId, StaffId expectedOwner) {
		UserSession session = sessionRepository.findById(sessionId)
				.orElseThrow(() -> new IllegalArgumentException("No session found with id " + sessionId.value()));
		
		if (!session.staffId().equals(expectedOwner)) {
			throw new IllegalArgumentException("Session does not belong to the specified user");
		}
		session.revoke();
		sessionRepository.save(session);
		
	}
}
