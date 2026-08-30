package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

import java.time.LocalDateTime;

import com.ai_powered_hms_backend.identity.domain.valueobjects.RoleAssignmentStatus;

//public record RoleAssignmentResponse(
//		String assignmentId, String staffId,
//		String roleId,String expireAt,boolean revoked) {
//}

public record RoleAssignmentResponse(
        String assignmentId,
        String staffId,
        String roleId,
        LocalDateTime assignedAt,
        String assignedBy,
        LocalDateTime expiresAt,
        RoleAssignmentStatus status
) {
}