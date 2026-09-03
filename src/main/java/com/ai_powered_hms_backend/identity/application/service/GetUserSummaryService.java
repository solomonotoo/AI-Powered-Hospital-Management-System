package com.ai_powered_hms_backend.identity.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.port.in.GetUserSummaryUseCase;
import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository;
import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository.UserCredentialSummary;

@Service
public class GetUserSummaryService implements GetUserSummaryUseCase{

	private final UserCredentialRepository credentialRepository;

	public GetUserSummaryService(UserCredentialRepository credentialRepository) {
		super();
		this.credentialRepository = credentialRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserCredentialSummary getSummary() {
		// TODO Auto-generated method stub
		return credentialRepository.getSummary();
	}
	
	
}
