package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

import java.util.List;

public record UserAccessResponse(List<RoleAssignmentResponse> roles,
		List<String> permissions) {

}
