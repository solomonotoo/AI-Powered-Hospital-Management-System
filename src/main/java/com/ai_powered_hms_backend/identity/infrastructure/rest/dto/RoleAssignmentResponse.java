package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

public record RoleAssignmentResponse(String assignmentId, String staffId,
		String roleId,String expireAt,boolean revoked) {

}
