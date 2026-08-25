package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

//staffId here is a plain UUID column (not a converted VO), so this derived
//query method works without needing the @Query-with-domain-type workaround
//we had to apply for Email/PhoneNumber fields elsewhere.

public interface UserSessionJpaRepository extends JpaRepository<UserSessionJpaEntity, UUID> {
	List<UserSessionJpaEntity> findByStaffId(UUID staffId);
}
