package com.ai_powered_hms_backend.identity.port.in;

import com.ai_powered_hms_backend.identity.application.port.in.CreateUserCredentialCommand;

public interface CreateUserCredentialUseCase {
	void create(CreateUserCredentialCommand command);
}
