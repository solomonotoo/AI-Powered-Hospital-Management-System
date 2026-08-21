package com.ai_powered_hms_backend.identity.application.port.in;

import com.ai_powered_hms_backend.identity.application.command.RefreshTokenCommand;
import com.ai_powered_hms_backend.identity.application.port.out.IssuedToken;

public interface RefreshTokenUseCase {

	 RefreshResult refresh(RefreshTokenCommand command);

	    record RefreshResult(
	            IssuedToken accessToken,
	            IssuedToken refreshToken,
	            String staffId,
	            String fullName,
	            String role,
	            boolean mustChangePassword
	    ) {
	    }
}
