package com.ai_powered_hms_backend.facility.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityStatus;
import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.FacilityCode;

// the implementation of methods in FacilityRepository in application.port.out.FacilityRepository

@Component
public class FacilityRepositoryAdaptor  implements FacilityRepository{

	private final FacilityJpaRepository jpaRepository;
	
	public FacilityRepositoryAdaptor(FacilityJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}
	
	@Override
	public void save(Facility facilty) {
		FacilityJpaEntity jpaEntity = FacilityPersistenceMapper.toEntity(facilty);
		jpaRepository.save(jpaEntity);
		
	}

	@Override
	public Optional<Facility> findById(FacilityId id) {
		// TODO Auto-generated method stub
		return jpaRepository.findById(id.value()) //capture the value of the UUID
				.map(FacilityPersistenceMapper :: toDomain);
	}

	@Override
	public Optional<Facility> findByCode(FacilityCode code) {
		// TODO Auto-generated method stub
		return jpaRepository.findByCode(code.value())
				.map(FacilityPersistenceMapper::toDomain);
	}

	@Override
	public boolean existsByCode(FacilityCode code) {
		// TODO Auto-generated method stub
		return jpaRepository.existsByCode(code.value());
	}

	@Override
	public List<Facility> findAllActive() {
		// TODO Auto-generated method stub
		return jpaRepository.findAllByStatus(FacilityStatus.ACTIVE)
				.stream().map(FacilityPersistenceMapper:: toDomain)
				.collect(Collectors.toList());
	}

}
