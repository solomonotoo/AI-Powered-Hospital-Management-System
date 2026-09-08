package com.ai_powered_hms_backend.identity.application.port.out;

import java.util.List;

import com.ai_powered_hms_backend.identity.domain.model.Permission;

public interface PermissionRepository {
	List<Permission> findAll();
	void save(Permission permission);
	boolean existsByCode(String code);
}
