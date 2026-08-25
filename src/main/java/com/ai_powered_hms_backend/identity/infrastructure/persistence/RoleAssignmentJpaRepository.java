package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleAssignmentJpaRepository extends JpaRepository<RoleAssignmentJpaEntity, UUID>{
	List<RoleAssignmentJpaEntity> findByStaffId(UUID staffId);
}
