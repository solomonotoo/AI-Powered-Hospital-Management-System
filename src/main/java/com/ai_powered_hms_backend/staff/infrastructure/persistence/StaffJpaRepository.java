package com.ai_powered_hms_backend.staff.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.staff.domain.enums.StaffRole;
import com.ai_powered_hms_backend.staff.domain.enums.StaffStatus;




//this is similar to repository interface in mvc
public interface StaffJpaRepository extends JpaRepository<StaffJpaEntity, UUID> {
	//employeeNumber is a plain @Column(name = "employee_number") String on the entity, 
	//not a converted type — that derived query works fine as-is.
	boolean existsByEmployeeNumber(String employeeNumber);
	
	//NB email is a converted type and will need this kind of query
	@Query("SELECT COUNT(s) > 0 FROM StaffJpaEntity s WHERE s.workEmail = :email")
	boolean existsByWorkEmailValue(@Param("email") Email email);

	Page<StaffJpaEntity> findByRole(StaffRole staffRole, Pageable pageable);
	
	long countByStatus(StaffStatus staffStatus);
	
	@Query("SELECT s.status AS status, COUNT(s) AS total FROM StaffJpaEntity s GROUP BY s.status")
	List<StaffStatusCount> countGroupedByStatus();
}
