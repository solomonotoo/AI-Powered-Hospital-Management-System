package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.identity.application.port.out.SessionRepository;
import com.ai_powered_hms_backend.identity.domain.model.UserSession;
import com.ai_powered_hms_backend.shared_kernel.ids.SessionId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;


//isValid(UUID) is already satisfied by the default method on the SessionRepository 
//interface itself — no override needed here.

@Component
public class SessionRepositoryAdaptor implements SessionRepository {

	private final UserSessionJpaRepository jpaRepository;
	
	public SessionRepositoryAdaptor(UserSessionJpaRepository jpaRepository) {
		super();
		this.jpaRepository = jpaRepository;
	}

	@Override
	public void save(UserSession session) {
		jpaRepository.save(UserSessionPersistenceMapper.toEntity(session));
		
	}

	@Override
	public Optional<UserSession> findById(SessionId id) {
		// TODO Auto-generated method stub
		return jpaRepository.findById(id.value()).map(UserSessionPersistenceMapper::toDomain);
	}

	@Override
	public List<UserSession> findByStaffId(StaffId staffId) {
		// TODO Auto-generated method stub
		return jpaRepository.findByStaffId(staffId.value()).stream()
				.map(UserSessionPersistenceMapper::toDomain)
				.collect(Collectors.toList());
	}

}
