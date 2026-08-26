package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

import java.util.Set;

public record RoleResponse(
		String roleId, String name, String description,
		Set<String> permissionCode, boolean systemDefault
		) {

}
