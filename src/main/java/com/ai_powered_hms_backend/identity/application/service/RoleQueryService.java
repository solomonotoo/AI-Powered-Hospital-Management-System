package com.ai_powered_hms_backend.identity.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ai_powered_hms_backend.identity.application.port.out.RoleRepository;
import com.ai_powered_hms_backend.identity.domain.model.Role;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleId;

@Service
public class RoleQueryService {

	private final RoleRepository roleRepository;
	
	public RoleQueryService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	public List<Role> listAll(){
		return roleRepository.findAll();
	}
	
	public Role getById(RoleId id) {
		return roleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("No role found with id " + id.value()));
	}
}
