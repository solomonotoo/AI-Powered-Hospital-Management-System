package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import com.ai_powered_hms_backend.identity.domain.model.Role;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataMapper;

public class RolePersistenceMapper {

	public static RoleJpaEntity toEntity(Role role) {
		return new RoleJpaEntity(
				role.roleId().value(),
				role.name(),
				role.description(),
				role.permissionCodes(),
				role.isSystemDefine(),
				AuditMetadataMapper.toEmbeddable(role.audit())
				);
	}
	
	public static Role toDomain(RoleJpaEntity entity) {
		return Role.reconstitute(
				RoleId.of(entity.getId()), 
				entity.getName(), 
				entity.getDescription(), 
				entity.getPermissionCodes(), 
				entity.isSystemDefind(), 
				AuditMetadataMapper.toDomain(entity.getAudit()));
	}
}
