package com.ai_powered_hms_backend.identity.application.query;

import java.time.LocalDateTime;

public record UserSummaryResult(
		String staffId,
		String fullName,
		String loginEmail,
		String staffRole, // job title, from StaffLookup — display only
		boolean active,
		boolean mustChangePassword,
		LocalDateTime lastLoginAt
		) {

}
