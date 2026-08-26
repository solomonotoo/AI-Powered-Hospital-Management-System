package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import java.util.Set;
import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataEmbeddable;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;


//@ElementCollection on Set<String> is the right tool here — permissionCodes 
//isn't a full aggregate relationship, just a simple set of strings owned 
//entirely by Role, backed by its own join table (role_permissions) with no 
//separate entity needed.

@Entity
@Table(name = "roles")
public class RoleJpaEntity {

	@Id
	private UUID id;
	
	@Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
	
	@Column(name = "description", length = 255)
    private String description;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name="role_id"))
	@Column(name = "permission_code")
    private Set<String> permissionCodes;
	
	@Column(name="system_defined",nullable = false)
	private boolean systemDefind;
	
	private AuditMetadataEmbeddable audit;
	protected RoleJpaEntity() {}

	
	public RoleJpaEntity(UUID id, String name, String description, Set<String> permissionCodes, boolean systemDefind,
			AuditMetadataEmbeddable audit) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.permissionCodes = permissionCodes;
		this.systemDefind = systemDefind;
		this.audit = audit;
	}


	public UUID getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getDescription() {
		return description;
	}


	public Set<String> getPermissionCodes() {
		return permissionCodes;
	}


	public boolean isSystemDefind() {
		return systemDefind;
	}


	public AuditMetadataEmbeddable getAudit() {
		return audit;
	}


	
	
	
	
}
