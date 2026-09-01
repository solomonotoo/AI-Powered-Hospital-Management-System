package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

public record UserSummaryResponse(
		String staffId,String fullName,String loginEmail,String staffRole,
		boolean active,boolean mustChangePassword, String lastLoginAt
		) {

}
