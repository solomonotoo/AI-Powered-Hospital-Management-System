package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

import com.ai_powered_hms_backend.identity.domain.model.Role;

public class RoleResponseMapper {

	public static RoleResponse toResponse(Role role) {
		return new RoleResponse(
				role.roleId().value().toString(),
				role.name(),role.description(),
				role.permissionCodes(),
				role.isSystemDefine()
				
				);
	}
}
