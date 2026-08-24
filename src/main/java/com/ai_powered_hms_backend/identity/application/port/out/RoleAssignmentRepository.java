package com.ai_powered_hms_backend.identity.application.port.out;

import java.util.List;
import java.util.Optional;

import com.ai_powered_hms_backend.identity.domain.model.RoleAssignment;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleAssignmentId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public interface RoleAssignmentRepository {
	void save(RoleAssignment assignment);
	Optional<RoleAssignment> findById(RoleAssignmentId id);
	List<RoleAssignment> findByStaffId(StaffId staffId);
}
