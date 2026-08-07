package com.ai_powered_hms_backend.staff.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;




//this is similar to repository interface in mvc
public interface StaffJpaRepository extends JpaRepository<StaffJpaEntity, UUID> {
	//employeeNumber is a plain @Column(name = "employee_number") String on the entity, 
	//not a converted type — that derived query works fine as-is.
	boolean existsByEmployeeNumber(String employeeNumber);
	
	//NB email is a converted type and will need this kind of query
	@Query("SELECT COUNT(s) > 0 FROM StaffJpaEntity s WHERE s.workEmail = :email")
	boolean existsByWorkEmailValue(@Param("email") Email email);
}
