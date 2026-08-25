package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.identity.application.port.out.UserActivityRepository;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

@Component
public class UserActivityRepositoryAdapter implements UserActivityRepository {

	private final UserActivityJpaRepository jpaRepository; 
	
	
	public UserActivityRepositoryAdapter(UserActivityJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}

	@Override
	public void record(StaffId staffId, String eventType, String description, UUID actorId) {
		jpaRepository.save(new UserActivityJpaEntity(
				UUID.randomUUID(),staffId.value(),eventType,description,
				actorId,LocalDateTime.now()
				));
		
	}

	@Override
	public List<UserActivityRecord> findByStaffId(StaffId staffId) {
		// TODO Auto-generated method stub
		return null;
	}

}

interface UserActivityJpaRepository  extends JpaRepository<UserActivityJpaEntity, UUID>{
    List<UserActivityJpaEntity> findByStaffIdOrderByOccurredAtDesc(UUID staffId);
}