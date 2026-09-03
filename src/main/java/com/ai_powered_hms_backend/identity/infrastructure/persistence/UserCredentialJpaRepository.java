package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;



public interface UserCredentialJpaRepository extends JpaRepository<UserCredentialJpaEntity, UUID> {

	@Query("SELECT u FROM UserCredentialJpaEntity u WHERE u.loginEmail = :email")
	Optional<UserCredentialJpaEntity> findByLoginEmail(@Param("email") Email email);
	
	@Query("SELECT COUNT(u) > 0 FROM UserCredentialJpaEntity u WHERE u.loginEmail = :email")
	boolean existsByLoginEmailValue(@Param("email") Email email);
	
	long countByActive(boolean active);
	long countByMfaEnabled(boolean mfaEnabled);
	
	@Query(
	        value = """
	            SELECT * FROM user_credentials u
	            WHERE (:search IS NULL OR LOWER(u.login_email) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%')))
	              AND (CAST(:active AS boolean) IS NULL OR u.is_active = CAST(:active AS boolean))
	              AND (CAST(:mfaEnabled AS boolean) IS NULL OR u.mfa_enabled = CAST(:mfaEnabled AS boolean))
	            """,
	        countQuery = """
	            SELECT count(*) FROM user_credentials u
	            WHERE (:search IS NULL OR LOWER(u.login_email) LIKE LOWER(CONCAT('%', CAST(:search AS text), '%')))
	              AND (CAST(:active AS boolean) IS NULL OR u.is_active = CAST(:active AS boolean))
	              AND (CAST(:mfaEnabled AS boolean) IS NULL OR u.mfa_enabled = CAST(:mfaEnabled AS boolean))
	            """,
	        nativeQuery = true
	    )
	Page<UserCredentialJpaEntity> search(
			@Param("search") String search,
			@Param("active") Boolean active,
			@Param("mfaEnabled") Boolean mfaEnabled,
			Pageable pageable
			);
}
