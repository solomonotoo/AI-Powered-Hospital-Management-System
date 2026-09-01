package com.ai_powered_hms_backend.identity.infrastructure.rest.mapper;

import com.ai_powered_hms_backend.identity.application.query.UserSummaryResult;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.UserSummaryResponse;

public class UserSumaryResponseMapper {
	public static UserSummaryResponse toResponse(UserSummaryResult r) {
		return new UserSummaryResponse(
				r.staffId(),
				r.fullName(),
				r.loginEmail(),
				r.staffRole(),
				r.active(),
				r.mustChangePassword(), 
				r.lastLoginAt() == null ? null : r.lastLoginAt().toString());
	}
}
