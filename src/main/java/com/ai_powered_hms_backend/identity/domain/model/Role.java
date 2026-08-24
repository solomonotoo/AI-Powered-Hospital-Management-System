package com.ai_powered_hms_backend.identity.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.base.AggregateRoot;
import com.ai_powered_hms_backend.shared_kernel.ids.RoleId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.AuditMetadata;

public final class Role extends AggregateRoot<RoleId>{

	 private String name;
	    private String description;
	    private Set<String> permissionCodes;
	    private boolean systemDefined; // seeded roles — cannot be deleted
	    private AuditMetadata audit;
	
	private Role(RoleId id, String name, String description, Set<String> permissionCodes,
            boolean systemDefined, UUID createdBy) {
        super(id);
        this.name = requireText(name);
        this.description = description;
        this.permissionCodes = new HashSet<>(Objects.requireNonNull(permissionCodes, "Permissions required"));
        this.systemDefined = systemDefined;
        this.audit = AuditMetadata.create(createdBy);
    }

    private Role(RoleId id, String name, String description, Set<String> permissionCodes,
            boolean systemDefined, AuditMetadata audit) {
        super(id);
        this.name = name;
        this.description = description;
        this.permissionCodes = new HashSet<>(permissionCodes);
        this.systemDefined = systemDefined;
        this.audit = audit;
    }
    
	 public static Role create(String name, String description, Set<String> permissionCodes, UUID createdBy) {
	        return new Role(RoleId.newId(), name, description, permissionCodes, false, createdBy);
	    }
	
	public static Role reconstitute(RoleId id,String name, String description, Set<String> permissionCodes,
			boolean systemDefined,AuditMetadata audit) {
		return new Role(id, name, description, permissionCodes, systemDefined, audit);
	}
	
	public void updatePermissions(Set<String> permissionCodes, UUID modifiedBy) {
		if(systemDefined) throw new IllegalStateException("Cannot modify a system-defined role");
		this.permissionCodes = new HashSet<>(permissionCodes);
		audit.update(modifiedBy);
	}
	
	
	private static String requireText(String value) {
		if(value == null || value.isBlank()) throw new IllegalArgumentException("Role name is required");
		return value.trim();
	}
	
	public RoleId roleId() {return getId();}
	public String name() {return name;}
	public String description() {return description;}
	public Set<String> permissionCodes(){return permissionCodes;}
	public boolean isSystemDefine() {return systemDefined;}
	public AuditMetadata audit() {return audit;}
}
