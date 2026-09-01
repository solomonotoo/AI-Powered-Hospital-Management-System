package com.ai_powered_hms_backend.identity.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.port.out.RoleAssignmentRepository;
import com.ai_powered_hms_backend.identity.application.port.out.RoleAssignmentRepository.RoleAssignmentPage;
import com.ai_powered_hms_backend.identity.application.query.AllRoleAssignmentResult;
import com.ai_powered_hms_backend.identity.domain.model.Role;
import com.ai_powered_hms_backend.identity.domain.model.RoleAssignment;
import com.ai_powered_hms_backend.identity.application.port.out.RoleRepository;
import com.ai_powered_hms_backend.staff.application.api.StaffLookup;

@Service
public class ListAllRoleAssignmentsService {

	private final RoleAssignmentRepository assignmentRepository;
	private final RoleRepository roleRepository;
	private final StaffLookup staffLookup;
	public ListAllRoleAssignmentsService(RoleAssignmentRepository assignmentRepository, RoleRepository roleRepository,
			StaffLookup staffLookup) {
		super();
		this.assignmentRepository = assignmentRepository;
		this.roleRepository = roleRepository;
		this.staffLookup = staffLookup;
	}
	
	
	@Transactional(readOnly = true)
	public RoleAssignmentPageResult list(int page, int size) {
		RoleAssignmentPage p = assignmentRepository.findAll(page, size);
		
		List<AllRoleAssignmentResult> content = p.content().stream()
				.map(this::enrich)
				.collect(Collectors.toList());
		
		return new RoleAssignmentPageResult(content, size, page, size);
	}
	
	private AllRoleAssignmentResult enrich(RoleAssignment a) {
		Role role = roleRepository.findById(a.roleId())
				.orElseThrow(() -> new IllegalArgumentException( "Data integrity error: assignment " + a.assignmentId().value()
                        + " references non-existent role " + a.roleId().value()));
	
	String staffName;
	try {
		staffName = staffLookup.getById(a.staffId().value()).fullName();
	} catch (IllegalArgumentException e) {
		staffName  = "(unknown staff)";
	}
	
	
	return new AllRoleAssignmentResult(
			a.assignmentId().value().toString(), a.staffId().value().toString(),
			staffName, role.roleId().value().toString(), role.name(), 
			a.expiresAt() == null ? null : a.expiresAt().toString(), a.isRevoked());
	
	}
	
	
	public record RoleAssignmentPageResult(List<AllRoleAssignmentResult> content, long totalElements, int page, int size) {}
	
	
}
