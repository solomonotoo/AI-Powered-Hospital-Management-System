package com.ai_powered_hms_backend.identity.application.port.out;

import java.util.List;
import java.util.Optional;

import com.ai_powered_hms_backend.identity.domain.model.Role;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleId;

public interface RoleRepository {
	void save(Role role);
	Optional<Role> findById(RoleId id);
	List<Role> findAll();
	boolean existsByName(String name);
}
