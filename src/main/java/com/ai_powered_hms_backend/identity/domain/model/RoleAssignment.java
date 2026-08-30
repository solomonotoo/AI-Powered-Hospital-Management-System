package com.ai_powered_hms_backend.identity.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.ai_powered_hms_backend.identity.domain.valueobjects.RoleAssignmentStatus;
import com.ai_powered_hms_backend.shared_kernel.base.AggregateRoot;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleAssignmentId;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.AuditMetadata;

public class RoleAssignment extends AggregateRoot<RoleAssignmentId>{

	private StaffId staffId;
	private RoleId roleId;
	private LocalDateTime expiresAt; //nullable -permanent if null
	private boolean revoked;
	private AuditMetadata audit;
	
	private RoleAssignment(RoleAssignmentId id, StaffId staffId, RoleId roleId, LocalDateTime expiresAt,
			UUID createdBy) {
		super(id);
		this.staffId = Objects.requireNonNull(staffId,"Staff id is required");
		this.roleId = Objects.requireNonNull(roleId, "Role id is required");
		this.expiresAt = expiresAt;
		this.revoked = false;
		this.audit = AuditMetadata.create(createdBy);
	}
	
	private RoleAssignment(RoleAssignmentId id, StaffId staffId, RoleId roleId, LocalDateTime expiresAt,
            boolean revoked, AuditMetadata audit) {
        super(id);
        this.staffId = staffId;
        this.roleId = roleId;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.audit = audit;
    }
	
	public static RoleAssignment assign(StaffId staffId,RoleId roleId, LocalDateTime expiresAt, UUID assignedBy) {
		return new RoleAssignment(RoleAssignmentId.newId(), staffId, roleId,expiresAt,assignedBy);
	}
	
	
	public static RoleAssignment reconstitute(RoleAssignmentId id, StaffId staffId, RoleId roleId,
			LocalDateTime expiresAt,boolean revoked, AuditMetadata audit ) {
		return new RoleAssignment(id, staffId, roleId, expiresAt, revoked, audit);
	}
	
	public void updateExpiry(LocalDateTime expiresAt, UUID modifiedBy) {
		this.expiresAt = expiresAt;
		audit.update(modifiedBy);
	}
	
	public void revoke(UUID modifiedBy) {
		  if (this.revoked) {
	            return;
	        }
		this.revoked = true;
		audit.update(modifiedBy);
	}
	
	public boolean isActive() {
		return !revoked && (expiresAt == null || expiresAt.isAfter(LocalDateTime.now()));
	}
	
	public RoleAssignmentStatus status() {
		if(revoked) {
			return RoleAssignmentStatus.REVOKED;
		}
		if(expiresAt != null && !expiresAt.isAfter(LocalDateTime.now())) {
			return RoleAssignmentStatus.EXPIRED;
		}
		
		return RoleAssignmentStatus.ACTIVE;
	}
	
	public RoleAssignmentId assignmentId() { return getId(); }
    public StaffId staffId() { return staffId; }
    public RoleId roleId() { return roleId; }
    public LocalDateTime expiresAt() { return expiresAt; }
    public boolean isRevoked() { return revoked; }
    public AuditMetadata audit() { return audit; }
}
