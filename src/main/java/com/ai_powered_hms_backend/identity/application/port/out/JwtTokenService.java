package com.ai_powered_hms_backend.identity.application.port.out;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public interface JwtTokenService {
	IssuedToken issue(StaffId staffId, String role);
	TokenClaims parse(String token);
}
