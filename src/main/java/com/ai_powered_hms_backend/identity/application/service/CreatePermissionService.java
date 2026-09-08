package com.ai_powered_hms_backend.identity.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.command.CreatePermissionCommand;
import com.ai_powered_hms_backend.identity.application.port.in.CreatePermissionUseCase;
import com.ai_powered_hms_backend.identity.application.port.in.CreateUserCredentialCommand;
import com.ai_powered_hms_backend.identity.application.port.out.PermissionRepository;
import com.ai_powered_hms_backend.identity.domain.model.Permission;

@Service
public class CreatePermissionService implements CreatePermissionUseCase {

	private final PermissionRepository permissionRepository;

	public CreatePermissionService(PermissionRepository permissionRepository) {
		super();
		this.permissionRepository = permissionRepository;
	}

	@Override
	@Transactional
	public void create(CreatePermissionCommand command) {
		String code = normalizeCode(command.code());
		
		if(permissionRepository.existsByCode(code)) {
			throw new DuplicatePermissionException("Permission code already exists: " + code);
		}
		
		permissionRepository.save(new Permission(code, command.description()));
		
	}
	
	private String normalizeCode(String code) {
		if(code == null || code.isBlank()) {
			throw new IllegalArgumentException("Permission code is required");
		}
		
		String normalized = code.trim().toUpperCase();
		if(!normalized.matches("^[A-Z0-9_]+$")) {
            throw new IllegalArgumentException("Permission code must be uppercase letters, digits, and underscores only");
        }
        return normalized;
	}

//	The matches("^[A-Z0-9_]+$") guard keeps every future permission code 
//	consistent with your existing seeded ones (PATIENT_READ, FACILITY_MANAGE, 
//	etc.) — prevents someone accidentally creating patient-read or Patient Read through the UI.
	

}
