package com.ai_powered_hms_backend.identity.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.port.out.RoleAssignmentRepository;
import com.ai_powered_hms_backend.identity.application.port.out.RoleRepository;
import com.ai_powered_hms_backend.identity.application.port.out.UserActivityRepository;
import com.ai_powered_hms_backend.identity.domain.model.RoleAssignment;
import com.ai_powered_hms_backend.identity.infrastructure.PermissionCacheConfig;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleAssignmentId;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.staff.application.api.StaffLookup;

@Service
public class UserAccessService {

	private final RoleAssignmentRepository assignmentRepository;
	private final RoleRepository roleRepository;
	private final UserActivityRepository activityRepository;
	private final StaffLookup staffLookup;
	
	public UserAccessService(RoleAssignmentRepository assignmentRepository, RoleRepository roleRepository,
			UserActivityRepository activityRepository, StaffLookup staffLookup) {
		super();
		this.assignmentRepository = assignmentRepository;
		this.roleRepository = roleRepository;
		this.activityRepository = activityRepository;
		this.staffLookup = staffLookup;
	}
	
	
	public List<RoleAssignment> listAssignments(StaffId staffId){
		return assignmentRepository.findByStaffId(staffId);
	}
	
	/**
     * [CACHE] This is the expensive call — 1 query for role assignments,
     * plus 1 query per active assignment to resolve each Role's permission set.
     * Cached for up to 60s (see PermissionCacheConfig) so JwtAuthenticationFilter
     * doesn't pay this cost on every single authenticated request.
     *
     * Cache key = staffId.value() (a UUID) — every distinct staff member gets
     * their own independent cache entry.
     */
    @Cacheable(value = PermissionCacheConfig.EFFECTIVE_PERMISSIONS_CACHE, key = "#staffId.value()")
    public Set<String> effectivePermissions(StaffId staffId) {
        return assignmentRepository.findByStaffId(staffId).stream()
                .filter(RoleAssignment::isActive)
                .map(a -> roleRepository.findById(a.roleId()).orElseThrow())
                .flatMap(r -> r.permissionCodes().stream())
                .collect(Collectors.toSet());
    }
	
 
    @Transactional
    // [CACHE] MUST evict — a newly assigned role must take effect without
    // waiting for the 60s TTL to naturally expire. Immediate eviction here
    // means "grant" is instant even though "revoke" (below) is also instant
    // for the same reason.
    @CacheEvict(value = PermissionCacheConfig.EFFECTIVE_PERMISSIONS_CACHE,key = "#staffId.value()")
    public RoleAssignment assignRole(StaffId staffId,RoleId roleId,LocalDateTime expiresAt, UUID assignedBy) {
    	staffLookup.getById(staffId.value());
    	roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("No role found with id " + roleId.value()));
    
    	RoleAssignment assignment = RoleAssignment.assign(staffId, roleId, expiresAt, assignedBy);
    	assignmentRepository.save(assignment);
    	activityRepository.record(staffId,  "ROLE_ASSIGNED", "Role " + roleId.value() + " assigned", assignedBy);
    	return assignment;
    }
	
	@Transactional
	 // [CACHE] MUST evict — see note on assignRole above. Without this,
    // a revoked role would still grant access for up to 60s.
    @CacheEvict(value = PermissionCacheConfig.EFFECTIVE_PERMISSIONS_CACHE, key = "#staffId.value()")
	public void revokeAssignment(StaffId staffId, RoleAssignmentId assignmentId, UUID modifiedBy) {
		  RoleAssignment assignment = assignmentRepository.findById(assignmentId)
	                .orElseThrow(() -> new IllegalArgumentException("No role assignment found with id " + assignmentId.value()));
	        assignment.revoke(modifiedBy);
	        assignmentRepository.save(assignment);
	        activityRepository.record(assignment.staffId(), "ROLE_REVOKED",
	                "Assignment " + assignmentId.value() + " revoked", modifiedBy);
	}
	
	@Transactional
	@CacheEvict(value = PermissionCacheConfig.EFFECTIVE_PERMISSIONS_CACHE, key = "#staffId.value()")
	public void updateAssignmentExpiry(StaffId staffId, RoleAssignmentId assignmentId, LocalDateTime expiresAt, UUID modifiedBy) {
		RoleAssignment assignment = assignmentRepository.findById(assignmentId)
				.orElseThrow(() -> new IllegalArgumentException("No role assignment found with id " + assignmentId.value()));
		assignment.updateExpiry(expiresAt, modifiedBy);
		assignmentRepository.save(assignment);
	}
	
	
//	Important signature change on revokeAssignment: it now takes StaffId staffId as an explicit parameter, purely so the @CacheEvict key 
//	expression (#staffId.value()) has something to reference — the original version only took assignmentId and derived staffId from the
//	loaded entity after the fact, which Spring's cache-eviction SpEL can't do (it evaluates the key expression from method parameters,
//			before the method body runs, not from values computed inside it). Update the controller call site:
	
	
	
}
