package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

public record RoleAssignmentSummaryResponse(
		String assignmentId, String staffId, String staffFullName,
		String roleId, String roleName,String expiresAt, boolean revoked
		) {

}
