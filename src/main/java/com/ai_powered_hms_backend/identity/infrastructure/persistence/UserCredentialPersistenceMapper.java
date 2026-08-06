package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import com.ai_powered_hms_backend.identity.domain.model.UserCredential;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataMapper;

public class UserCredentialPersistenceMapper {

	public static UserCredentialJpaEntity toEntity(UserCredential c) {
		return new UserCredentialJpaEntity(
				c.staffId().value(), c.loginEmail(),c.passwordHash(),
				c.isMfaEnabled(), c.mustChangePassword(), c.lastLoginAt(),
				c.isActive(), AuditMetadataMapper.toEmbeddable(c.audit())
				);
	}
	
	public static UserCredential toDomain(UserCredentialJpaEntity e) {
		return UserCredential.reconstitute(
				StaffId.of(e.getStaffId()), 
				e.getLoginEmail(), e.getPasswordHash(),e.isMfaEnabled(),
				e.isMustChangePassword(),e.getLastLoginAt(), e.isActive(), 
				AuditMetadataMapper.toDomain(e.getAudit())
				);
	}
}
