package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;



public interface UserCredentialJpaRepository extends JpaRepository<UserCredentialJpaEntity, UUID> {

	@Query("SELECT u FROM UserCredentialJpaEntity u WHERE u.loginEmail = :email")
	Optional<UserCredentialJpaEntity> findByLoginEmail(@Param("email") Email email);
	
	@Query("SELECT COUNT(u) > 0 FROM UserCredentialJpaEntity u WHERE u.loginEmail = :email")
	boolean existsByLoginEmailValue(@Param("email") Email email);
}
