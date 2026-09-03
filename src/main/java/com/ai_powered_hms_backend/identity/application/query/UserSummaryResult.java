package com.ai_powered_hms_backend.identity.application.query;

import java.time.LocalDateTime;

import com.ai_powered_hms_backend.identity.domain.valueobjects.UserAccountStatus;

public record UserSummaryResult(
		String staffId,
		String fullName,
		String loginEmail,
		String staffRole, // job title, from StaffLookup — display only
		//boolean active,
		UserAccountStatus status,
		boolean mustChangePassword,
		LocalDateTime lastLoginAt
		) {

}
