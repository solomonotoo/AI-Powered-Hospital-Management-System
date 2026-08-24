package com.ai_powered_hms_backend.identity.application.port.out;

import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public interface JwtTokenService {
//	IssuedToken issueAccessToken(StaffId staffId, String role);
//    IssuedToken issueRefreshToken(StaffId staffId, String role);
	
	IssuedToken issueAccessToken(StaffId staffId, String role, UUID sessionId);
    IssuedToken issueRefreshToken(StaffId staffId, String role, UUID sessionId);

	// IssuedTokenPair issueTokens(StaffId staffId, String role);
    TokenClaims parseAccessToken(String token);

    TokenClaims parseRefreshToken(String token);
}
