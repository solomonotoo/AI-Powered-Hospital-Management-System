package com.ai_powered_hms_backend.identity.application.query;

public record AllRoleAssignmentResult(
		String assignmentId,String staffId,String staffFullName,
		String roleId,String roleName,String expiresAt,boolean revoked
		) {

}
