package com.ai_powered_hms_backend.identity.infrastructure.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, String> {

}
