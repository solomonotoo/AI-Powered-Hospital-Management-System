package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

public record UserSummaryCardResponse(
		long totalUsers,
		long activeUsers,
		long mfaEnabledUsers,
		long suspendedUsers
		) {

}
