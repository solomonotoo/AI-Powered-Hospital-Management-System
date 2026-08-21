package com.ai_powered_hms_backend.facility.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai_powered_hms_backend.facility.domain.eums.FacilityStatus;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;

//this file represent the jpa repository and it will be invoked by FacilityRepositoryAdaptor

public interface FacilityJpaRepository extends JpaRepository<FacilityJpaEntity, UUID>{

	Optional<FacilityJpaEntity> findByCode(String code);
	boolean existsByCode(String code);
	List<FacilityJpaEntity> findAllByStatus(FacilityStatus status);
	
	Page<FacilityJpaEntity> findAllByStatusAndType(FacilityStatus status,FacilityType type, Pageable pageable);
	Page<FacilityJpaEntity> findAllByStatus(FacilityStatus status,Pageable pageable);
	Page<FacilityJpaEntity> findAllByType(FacilityType type, Pageable pageable);
	Page<FacilityJpaEntity> findAll(Pageable pageable);
	
	long countByStatus(FacilityStatus status);

//	Spring Data's interface-based projection (FacilityTypeCount) lets a GROUP BY query return typed rows directly, 
//	without needing a separate DTO-mapping layer — the JPQL alias names (type, total) match the projection's getter 
//	names (getType(), getTotal()) by convention.
   
	@Query("SELECT f.type AS type, COUNT(f) AS total FROM FacilityJpaEntity f GROUP BY f.type")
    List<FacilityTypeCount> countGroupedByType();

    interface FacilityTypeCount {
        FacilityType getType();
        Long getTotal();
    }
}
