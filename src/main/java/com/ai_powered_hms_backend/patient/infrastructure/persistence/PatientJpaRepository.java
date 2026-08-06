package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PatientJpaRepository extends JpaRepository<PatientJpaEntity, UUID>{
//i will check this code later
//	Optional<PatientJpaEntity>  findByMrnFacilityCode(String mrnFacilityCode);
//
//    boolean existsByMrnFacilityCodeAndMrnValue(
//            String mrnFacilityCode,
//            String mrnValue);
}
