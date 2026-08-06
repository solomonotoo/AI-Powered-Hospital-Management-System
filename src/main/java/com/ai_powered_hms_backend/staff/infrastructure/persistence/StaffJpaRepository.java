package com.ai_powered_hms_backend.staff.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


//this is similar to repository interface in mvc
public interface StaffJpaRepository extends JpaRepository<StaffJpaEntity, UUID> {

	boolean existsByEmployeeNumber(String employeeNumber);
	boolean existsByWorkEmail(String workEmail);
}
