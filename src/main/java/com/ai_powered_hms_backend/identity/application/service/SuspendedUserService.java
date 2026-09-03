package com.ai_powered_hms_backend.identity.application.service;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.port.in.SuspendedUserUseCase;
import com.ai_powered_hms_backend.identity.application.port.out.SessionRepository;
import com.ai_powered_hms_backend.identity.application.port.out.UserActivityRepository;
import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository;
import com.ai_powered_hms_backend.identity.domain.model.UserCredential;
import com.ai_powered_hms_backend.identity.domain.model.UserSession;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

@Service
public class SuspendedUserService implements SuspendedUserUseCase {

	private final UserCredentialRepository  credentialRepository;
	private final SessionRepository sessionRepository;
	private final UserActivityRepository activityRepository;

	public SuspendedUserService(UserCredentialRepository credentialRepository, SessionRepository sessionRepository,
			UserActivityRepository activityRepository) {
		super();
		this.credentialRepository = credentialRepository;
		this.sessionRepository = sessionRepository;
		this.activityRepository = activityRepository;
	}

	@Override
	@Transactional
	public void suspend(StaffId staffId, UUID suspendedBy) {
		UserCredential credential = credentialRepository.findByStaffId(staffId)
				.orElseThrow(() -> new IllegalArgumentException("No credential found for staff " + staffId.value()));
		
		credential.deactivate(suspendedBy);
		credentialRepository.save(credential);
		
		// The forcible part: kill every live session immediately, not just
        // block future logins. Without this, an already-issued access token
        // remains valid until its own short expiry, and a still-live refresh
        // token would only be caught the next time it's actually used.
		List<UserSession> sessions = sessionRepository.findByStaffId(staffId);
		for (UserSession session : sessions) {
			if(!session.isRevoked()) {
				session.revoke();
				sessionRepository.save(session);
			}
		}
		
		activityRepository.record(staffId, "ACCOUNT_SUPPENDED", "Account suspended, " + sessions.size() + " sessions(s) revoked", suspendedBy);
		
	}

	@Override
	@Transactional
	public void reactivate(StaffId staffId, UUID reactivatedBy) {
		UserCredential credential = credentialRepository.findByStaffId(staffId)
				.orElseThrow(() -> new IllegalArgumentException("No credential found for staff " + staffId.value()));
		
		credential.reactivate(reactivatedBy);
		credentialRepository.save(credential);
		
		activityRepository.record(staffId, "ACCOUNT_REACTIVATED", "Account reactivated", reactivatedBy);
		
	}

}
