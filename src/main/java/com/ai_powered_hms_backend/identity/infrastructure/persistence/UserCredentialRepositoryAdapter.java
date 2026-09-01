package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository;
import com.ai_powered_hms_backend.identity.domain.model.UserCredential;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;

@Component
public class UserCredentialRepositoryAdapter implements UserCredentialRepository{

	private final UserCredentialJpaRepository jpaRepository;
	
	private UserCredentialRepositoryAdapter(UserCredentialJpaRepository jpaRepository) {
		super();
		this.jpaRepository = jpaRepository;
	}

	@Override
	public void save(UserCredential credential) {
		this.jpaRepository.save(UserCredentialPersistenceMapper.toEntity(credential));
		
	}

	@Override
	public Optional<UserCredential> findByStaffId(StaffId staffId) {
		// TODO Auto-generated method stub
		return jpaRepository.findById(staffId.value())
				.map(UserCredentialPersistenceMapper :: toDomain);
	}

	@Override
	public Optional<UserCredential> findByLoginEmail(String email) {
		// TODO Auto-generated method stub
		return jpaRepository.findByLoginEmail(new Email(email))
				.map(UserCredentialPersistenceMapper :: toDomain);
	}

	@Override
	public boolean existsByLoginEmail(String email) {
		// TODO Auto-generated method stub
		return jpaRepository.existsByLoginEmailValue(new Email(email));
	}

	@Override
	public UserCredentialPage findAll(int page, int size) {
		Page<UserCredentialJpaEntity> result = jpaRepository.findAll(
				PageRequest.of(page, size, Sort.by("loginEmail"))
				);
		
		List<UserCredential> content = result.getContent().stream()
				.map(UserCredentialPersistenceMapper :: toDomain)
				.collect(Collectors.toList());
		return new UserCredentialPage(content,result.getTotalElements(), page,size);
	}

}
