package com.ai_powered_hms_backend.identity.infrastructure.persistence;


import com.ai_powered_hms_backend.identity.domain.model.RoleAssignment;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleAssignmentId;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataMapper;

public class RoleAssignmentPersistenceMapper {

	public static RoleAssignmentJpaEntity toEntity(RoleAssignment assignment) {
		return new RoleAssignmentJpaEntity(
                assignment.assignmentId().value(), assignment.staffId().value(), assignment.roleId().value(),
                assignment.expiresAt(), assignment.isRevoked(),
                AuditMetadataMapper.toEmbeddable(assignment.audit())
        );
	}
	
	public static RoleAssignment toDomain(RoleAssignmentJpaEntity entity) {
		return RoleAssignment.reconstitute(RoleAssignmentId.of(entity.getId()),
				StaffId.of(entity.getId()), RoleId.of(entity.getId()), 
				entity.getExpiresAt(), entity.isRevoked(), 
				AuditMetadataMapper.toDomain(entity.getAudit()));
	}
}
