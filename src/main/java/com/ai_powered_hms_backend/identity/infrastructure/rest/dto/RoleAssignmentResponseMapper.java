package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

import com.ai_powered_hms_backend.identity.domain.model.RoleAssignment;

public class RoleAssignmentResponseMapper {

//	public static RoleAssignmentResponse toResponse(RoleAssignment a) {
//		return new RoleAssignmentResponse(
//				a.assignmentId().value().toString(),
//				a.staffId().value().toString(),
//				a.roleId().value().toString(),
//				a.expiresAt() == null ? null : a.expiresAt().toString(),
//				a.isRevoked()
//				);
//	}
	
	
	public static RoleAssignmentResponse toResponse(RoleAssignment assignment) {
		return new RoleAssignmentResponse(
		        assignment.assignmentId().value().toString(),
		        assignment.staffId().value().toString(),
		        assignment.roleId().value().toString(),
		        assignment.audit().getCreatedAt(),
		        assignment.audit().getCreatedBy().toString(),
		        assignment.expiresAt(),
		        assignment.status()
		);
	}

}
