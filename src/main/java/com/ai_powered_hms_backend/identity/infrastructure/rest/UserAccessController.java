package com.ai_powered_hms_backend.identity.infrastructure.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_powered_hms_backend.identity.application.port.out.PermissionRepository;
import com.ai_powered_hms_backend.identity.application.port.out.RoleRepository;
import com.ai_powered_hms_backend.identity.application.service.PermissionQueryService;
import com.ai_powered_hms_backend.identity.application.service.RoleQueryService;
import com.ai_powered_hms_backend.identity.application.service.SessionQueryService;
import com.ai_powered_hms_backend.identity.application.service.UserAccessService;
import com.ai_powered_hms_backend.identity.application.service.UserActivityQueryService;
import com.ai_powered_hms_backend.identity.domain.model.RoleAssignment;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.AssignRoleRequest;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.PermissionResponse;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.RoleAssignmentResponse;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.RoleAssignmentResponseMapper;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.RoleResponse;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.RoleResponseMapper;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.SessionResponse;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.UpdateAssignmentRequest;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.UserAccessResponse;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.UserActivityResponse;
import com.ai_powered_hms_backend.identity.infrastructure.rest.mapper.SessionResponseMapper;
import com.ai_powered_hms_backend.identity.infrastructure.rest.mapper.UserActivityResponseMapper;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleAssignmentId;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleId;
import com.ai_powered_hms_backend.shared_kernel.ids.SessionId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.security.CurrentUserId;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class UserAccessController {

	 private static final String SELF_OR_ADMIN =
		        "#userId.toString() == authentication.principal.staffId().value().toString() or hasAuthority('USER_MANAGE')";

	 private final RoleQueryService roleQueryService;
	 private final PermissionQueryService permissionQueryService;
	 private final UserAccessService userAccessService;
	 private final SessionQueryService sessionQueryService;
	 private final UserActivityQueryService activityQueryService;
	 
	 public UserAccessController(RoleQueryService roleQueryService, PermissionQueryService permissionQueryService,
			UserAccessService userAccessService, SessionQueryService sessionQueryService,
			UserActivityQueryService activityQueryService) {
		super();
		this.roleQueryService = roleQueryService;
		this.permissionQueryService = permissionQueryService;
		this.userAccessService = userAccessService;
		this.sessionQueryService = sessionQueryService;
		this.activityQueryService = activityQueryService;
	}

	// ---------------------------------------------------------------
	    // Role & permission catalog (admin-managed reference data)
	    // ---------------------------------------------------------------

	 @GetMapping("/roles")
	    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
	 public ResponseEntity<List<RoleResponse>> listRole(){
		 List<RoleResponse> roles = roleQueryService.listAll().stream()
				 .map(RoleResponseMapper::toResponse)
				 .collect(Collectors.toList());
		 return ResponseEntity.ok(roles);
	 }
	 
	 @GetMapping("/roles/{roleId}")
	 @PreAuthorize("hasAuthority('ROLE_MANAGE')")
	 public ResponseEntity<RoleResponse> getRole(@PathVariable UUID roleId) {
	        return ResponseEntity.ok(RoleResponseMapper.toResponse(roleQueryService.getById(RoleId.of(roleId))));
	    }
	 
	 @GetMapping("/permissions")
	 @PreAuthorize("hasAuthority('ROLE__MANAGE')")
	 public ResponseEntity<List<PermissionResponse>> listPermission(){
		 List<PermissionResponse> permissions = permissionQueryService.listAll().stream()
				 .map(p -> new PermissionResponse(p.code(), p.description()))
				 .collect(Collectors.toList());
		return ResponseEntity.ok(permissions);	 
	 }
	 
	// ---------------------------------------------------------------
	    // Per-user access (self, or an admin holding USER_MANAGE)
	    // ---------------------------------------------------------------


	 @GetMapping("/users/{userId}/roles")
	    @PreAuthorize(SELF_OR_ADMIN)
	    public ResponseEntity<List<RoleAssignmentResponse>> listUserRoles(@PathVariable UUID userId) {
	        List<RoleAssignmentResponse> assignments = userAccessService.listAssignments(StaffId.of(userId)).stream()
	                .map(RoleAssignmentResponseMapper::toResponse)
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(assignments);
	    }
	 
	  @GetMapping("/users/{userId}/permissions")
	    @PreAuthorize(SELF_OR_ADMIN)
	    public ResponseEntity<List<String>> listUserPermissions(@PathVariable UUID userId) {
	        List<String> permissions = userAccessService.effectivePermissions(StaffId.of(userId)).stream()
	                .sorted()
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(permissions);
	    }

	    @GetMapping("/users/{userId}/access")
	    @PreAuthorize(SELF_OR_ADMIN)
	    public ResponseEntity<UserAccessResponse> getAccess(@PathVariable UUID userId) {
	        StaffId staffId = StaffId.of(userId);

	        List<RoleAssignmentResponse> roles = userAccessService.listAssignments(staffId).stream()
	                .map(RoleAssignmentResponseMapper::toResponse)
	                .collect(Collectors.toList());

	        List<String> permissions = userAccessService.effectivePermissions(staffId).stream()
	                .sorted()
	                .collect(Collectors.toList());

	        return ResponseEntity.ok(new UserAccessResponse(roles, permissions));
	    }
	
	    
	    // ---------------------------------------------------------------
	    // Role assignment mutation (ROLE_MANAGE only)
	    // ---------------------------------------------------------------
  
	    @PostMapping("/users/{userId}/roles")
	    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
	    public ResponseEntity<RoleAssignmentResponse> assignRole(
	            @PathVariable UUID userId,
	            @RequestBody AssignRoleRequest request,
	            @CurrentUserId UUID currentUserId
	    ) {
	        RoleAssignment assignment = userAccessService.assignRole(
	                StaffId.of(userId), RoleId.of(request.roleId()), request.expiresAt(), currentUserId
	        );
	        return ResponseEntity.status(HttpStatus.CREATED).body(RoleAssignmentResponseMapper.toResponse(assignment));
	    }

	    @PutMapping("/users/{userId}/roles/{assignmentId}")
	    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
	    public ResponseEntity<Void> updateAssignment(
	            @PathVariable UUID userId,
	            @PathVariable UUID assignmentId,
	            @RequestBody UpdateAssignmentRequest request,
	            @CurrentUserId UUID currentUserId
	    ) {
	        userAccessService.updateAssignmentExpiry(
	                StaffId.of(userId), RoleAssignmentId.of(assignmentId), request.expiresAt(), currentUserId
	        );
	        return ResponseEntity.noContent().build();
	    }

//	    @DeleteMapping("/users/{userId}/roles/{assignmentId}")
//	    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
//	    public ResponseEntity<Void> revokeAssignment(
//	            @PathVariable UUID userId,
//	            @PathVariable UUID assignmentId,
//	            @CurrentUserId UUID currentUserId
//	    ) {
//	        userAccessService.revokeAssignment(StaffId.of(userId), RoleAssignmentId.of(assignmentId), currentUserId);
//	        return ResponseEntity.noContent().build();
//	    } 
	    @DeleteMapping("/users/{userId}/roles/{assignmentId}")
	    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
	    public ResponseEntity<Void> revokeAssignment(
	            @PathVariable UUID userId,
	            @PathVariable UUID assignmentId,
	            @CurrentUserId UUID currentUserId
	    ) {
	        userAccessService.revokeAssignment(StaffId.of(userId), RoleAssignmentId.of(assignmentId), currentUserId);
	        return ResponseEntity.noContent().build();
	    } 
	    
	 // ---------------------------------------------------------------
	    // Activity & sessions (self, or an admin holding USER_MANAGE)
	    // ---------------------------------------------------------------
	    @GetMapping("/users/{userId}/activity")
	    @PreAuthorize(SELF_OR_ADMIN)
	    public ResponseEntity<List<UserActivityResponse>> getActivity(@PathVariable UUID userId) {
	        List<UserActivityResponse> activity = activityQueryService.listForUser(StaffId.of(userId)).stream()
	                .map(UserActivityResponseMapper::toResponse)
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(activity);
	    }
	    
	 @GetMapping("/users/{userId}/sessions")
		@PreAuthorize(SELF_OR_ADMIN)
		public ResponseEntity<List<SessionResponse>> getSessions(@PathVariable UUID userId){
			List<SessionResponse> sessions = sessionQueryService.listForUser(StaffId.of(userId))
					.stream()
					.map(SessionResponseMapper::toResponse)
					.collect(Collectors.toList());
			return ResponseEntity.ok(sessions);
		}

		@DeleteMapping("/users/{userId}/sessions/{sessionId}")
		@PreAuthorize(SELF_OR_ADMIN)
		public ResponseEntity<Void> revokeSession(
				@PathVariable UUID userId,
				@PathVariable UUID sessionId
				) {
		    sessionQueryService.revokeIfOwnedBy(SessionId.of(sessionId), StaffId.of(userId));
		    return ResponseEntity.noContent().build();
	}
}
