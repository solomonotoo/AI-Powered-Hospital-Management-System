package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.identity.application.port.out.RoleRepository;
import com.ai_powered_hms_backend.identity.domain.model.Role;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleId;

@Component
public class RoleRepositoryAdaptor implements RoleRepository{

	private final RoleJpaRepository jpaRepository;
	
	public RoleRepositoryAdaptor(RoleJpaRepository jpaRepository) {
		super();
		this.jpaRepository = jpaRepository;
	}

	@Override
	public void save(Role role) {
		jpaRepository.save(RolePersistenceMapper.toEntity(role));
		
	}

	@Override
	public Optional<Role> findById(RoleId id) {
		// TODO Auto-generated method stub
		return jpaRepository.findById(id.value())
				.map(RolePersistenceMapper::toDomain);
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return jpaRepository.findAll().stream()
				.map(RolePersistenceMapper::toDomain)
				.collect(Collectors.toList());
	}

	@Override
	public boolean existsByName(String name) {
		// TODO Auto-generated method stub
		return jpaRepository.existsByName(name);
	}

}
