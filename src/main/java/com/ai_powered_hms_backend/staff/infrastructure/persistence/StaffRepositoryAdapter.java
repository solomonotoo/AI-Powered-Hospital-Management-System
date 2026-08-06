package com.ai_powered_hms_backend.staff.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.staff.application.port.out.StaffRepository;
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
		return jpaRepository.existsByWorkEmail(workEmail);
	}

}
