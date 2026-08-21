package com.ai_powered_hms_backend.staff.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.staff.application.port.out.StaffPage;
import com.ai_powered_hms_backend.staff.application.port.out.StaffRepository;
import com.ai_powered_hms_backend.staff.application.query.StaffSummaryResult;
import com.ai_powered_hms_backend.staff.domain.enums.StaffRole;
import com.ai_powered_hms_backend.staff.domain.enums.StaffStatus;
import com.ai_powered_hms_backend.staff.domain.model.StaffProfile;
import com.ai_powered_hms_backend.staff.domain.valueobjects.EmployeeNumber;


@Component
public class StaffRepositoryAdapter implements StaffRepository{
	
	private final StaffJpaRepository jpaRepository;
	
	public StaffRepositoryAdapter(StaffJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public void save(StaffProfile staff) {
		//NOTE toEntity is used here because we are saving to the database
		jpaRepository.save(StaffPersistenceMapper.toEntity(staff));
		
	}

	@Override
	public Optional<StaffProfile> findById(StaffId id) {
		// toDomain is used here because we retrieve a value the database 
		// we need that value in a domain form
		return jpaRepository.findById(id.value())
				.map(StaffPersistenceMapper :: toDomain);
	}

	@Override
	public boolean existsByEmployeeNumber(EmployeeNumber employeeNumber) {
		// TODO Auto-generated method stub
		return jpaRepository.existsByEmployeeNumber(employeeNumber.value());
	}

	@Override
	public boolean existsByWorkEmail(String workEmail) {
		// TODO Auto-generated method stub
		return jpaRepository.existsByWorkEmailValue(new Email(workEmail));
	}

	@Override
	public StaffPage findAll(StaffRole staffRole, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("fullName").ascending());
		Page<StaffJpaEntity> result;
		
		if(staffRole != null ) {
			result = jpaRepository.findByRole(staffRole,pageable);
		}else {
			result = jpaRepository.findAll(pageable);
		}
		
		List<StaffProfile> contentList = result.getContent().stream()
				.map(StaffPersistenceMapper :: toDomain)
				.collect(Collectors.toList());
		
		return new StaffPage(contentList, result.getTotalElements(),page,size);
	}

	@Override
	public StaffSummaryResult getSummary() {
		long active = jpaRepository.countByStatus(StaffStatus.ACTIVE);
		long inactive = jpaRepository.countByStatus(StaffStatus.INACTIVE);
		long onDuty = jpaRepository.countByStatus(StaffStatus.ON_DUTY);
		long onLeave = jpaRepository.countByStatus(StaffStatus.ON_LEAVE);
		
		return new StaffSummaryResult(onLeave, active, inactive, onDuty, onLeave);
	}

}
