package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.identity.application.port.out.RoleAssignmentRepository;
import com.ai_powered_hms_backend.identity.domain.model.RoleAssignment;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleAssignmentId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

@Component
public class RoleAssignmentRepositoryAdapter implements RoleAssignmentRepository {

	private final RoleAssignmentJpaRepository jpaRepository;
    public RoleAssignmentRepositoryAdapter(RoleAssignmentJpaRepository jpaRepository) { this.jpaRepository = jpaRepository; }

	
	@Override
	public void save(RoleAssignment assignment) {
		jpaRepository.save(RoleAssignmentPersistenceMapper.toEntity(assignment));
		
	}

	@Override
	public Optional<RoleAssignment> findById(RoleAssignmentId id) {
		// TODO Auto-generated method stub
		return jpaRepository.findById(id.value())
				.map(RoleAssignmentPersistenceMapper::toDomain);
	}

	@Override
	public List<RoleAssignment> findByStaffId(StaffId staffId) {
		// TODO Auto-generated method stub
		return jpaRepository.findByStaffId(staffId.value()).stream()
				.map(RoleAssignmentPersistenceMapper::toDomain)
				.collect(Collectors.toList());
	}

}
