package com.ai_powered_hms_backend.identity.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ai_powered_hms_backend.identity.application.port.out.PermissionRepository;
import com.ai_powered_hms_backend.identity.domain.model.Permission;

@Service
public class PermissionQueryService {
	private final PermissionRepository permissionRepository;

	public PermissionQueryService(PermissionRepository permissionRepository) {
		super();
		this.permissionRepository = permissionRepository;
	}
	
	public List<Permission> listAll(){
		return permissionRepository.findAll();
	}
}
