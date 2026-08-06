package com.ai_powered_hms_backend.identity.application.port.in;

import com.ai_powered_hms_backend.identity.application.command.AuthenticationCommand;
import com.ai_powered_hms_backend.identity.application.port.out.IssuedToken;

public interface AuthenticateUseCase {
	AuthResult authentication(AuthenticationCommand command);
	
	record AuthResult(IssuedToken token, String staffId, String fullName, String role,
			boolean mustChangePassword) {}
}
