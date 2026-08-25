package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.identity.application.port.out.PermissionRepository;
import com.ai_powered_hms_backend.identity.domain.model.Permission;


@Component
public class PermissionRepositoryAdapter implements PermissionRepository{

	  private final PermissionJpaRepository jpaRepository;
	    public PermissionRepositoryAdapter(PermissionJpaRepository jpaRepository) { this.jpaRepository = jpaRepository; }

	    
	@Override
	public List<Permission> findAll() {
		// TODO Auto-generated method stub
		return jpaRepository.findAll().stream()
				.map(e -> new Permission(e.getCode(), e.getDescription()))
				.collect(Collectors.toList());
	}

}
