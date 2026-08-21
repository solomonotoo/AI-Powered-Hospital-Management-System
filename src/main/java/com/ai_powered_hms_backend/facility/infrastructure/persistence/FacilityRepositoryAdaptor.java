package com.ai_powered_hms_backend.facility.infrastructure.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.facility.application.port.out.FacilityPage;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.facility.application.query.FacilitySummaryResult;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityStatus;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;
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

	@Override
	public FacilityPage findAll(FacilityStatus status, FacilityType type, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
		Page<FacilityJpaEntity> result;
		
		if(status != null && type != null) {
			result = jpaRepository.findAllByStatusAndType(status, type, pageable);
		}else if (status != null) {
			result = jpaRepository.findAllByStatus(status, pageable);
		}else if (type != null) {
			result = jpaRepository.findAllByType(type, pageable);
		}else {
			result = jpaRepository.findAll(pageable);
		}
		
		
		List<Facility> content = result.getContent().stream()
				.map(FacilityPersistenceMapper :: toDomain)
				.collect(Collectors.toList());
		
		return new FacilityPage(content, result.getTotalElements(),page,size);
	}

//	Three lightweight queries total (two COUNT, one GROUP BY) — no facility rows ever 
//	loaded into the JVM just to be counted, which matters once you have hundreds of facilities 
//	across regions.
	@Override
	public FacilitySummaryResult getSummary() {
		long active = jpaRepository.countByStatus(FacilityStatus.ACTIVE);
		long inactive = jpaRepository.countByStatus(FacilityStatus.INACTIVE);
		long pending = jpaRepository.countByStatus(FacilityStatus.PENDING_APPROVAL);
		
		Map<FacilityType, Long> byType = jpaRepository.countGroupedByType().stream()
				.collect(Collectors.toMap(
						FacilityJpaRepository.FacilityTypeCount::getType,
						FacilityJpaRepository.FacilityTypeCount::getTotal
						));
		
		return new FacilitySummaryResult(active + inactive, active, inactive, pending, byType);
	}
	
	

}
