package com.ai_powered_hms_backend.identity.application.port.in;

import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository.UserCredentialSummary;

public interface GetUserSummaryUseCase {
	UserCredentialSummary getSummary();
}
