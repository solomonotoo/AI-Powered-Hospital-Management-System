package com.ai_powered_hms_backend.shared_kernel.valueobjects;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//@Embeddable
public class AuditMetadata {

	//moved to  AuditMetadataEmbeddable.java
//	@Column(name = "created_at", nullable = false, updatable = false)
//	private LocalDateTime createdAt;
//
//	@Column(name = "updated_at")
//	private LocalDateTime updatedAt;
//
//	@Column(name = "created_by")
//	private UUID createdBy;
//
//	@Column(name = "updated_by")
//	private UUID updatedBy;
//
//	protected AuditMetadata() {
//		// JPA
//	}
//
//	private AuditMetadata(UUID createdBy) {
//
//		this.createdBy = createdBy;
//
//		this.createdAt = LocalDateTime.now();
//
//		this.updatedAt = LocalDateTime.now();
//	}
//
//	public static AuditMetadata create(UUID createdBy) {
//
//		return new AuditMetadata(createdBy);
//	}
//
//	public void update(UUID userId) {
//
//		this.updatedBy = userId;
//
//		this.updatedAt = LocalDateTime.now();
//	}
//
//	public LocalDateTime getCreatedAt() {
//
//		return createdAt;
//	}
//
//	public LocalDateTime getUpdatedAt() {
//
//		return updatedAt;
//	}
//
//	public UUID getCreatedBy() {
//
//		return createdBy;
//	}
//
//	public UUID getUpdatedBy() {
//
//		return updatedBy;
//	}
	
	 private final LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    private final UUID createdBy;
	    private UUID updatedBy;

	    private AuditMetadata(UUID createdBy, LocalDateTime createdAt, LocalDateTime updatedAt, UUID updatedBy) {
	    	 this.createdBy = Objects.requireNonNull(createdBy, "Created by must not be null");
	        this.createdAt = createdAt;
	        this.updatedAt = updatedAt;
	        this.updatedBy = updatedBy;
	    }

	    public static AuditMetadata create(UUID createdBy) {
	        Objects.requireNonNull(createdBy, "Created by must not be null");
	        LocalDateTime now = LocalDateTime.now();
	        return new AuditMetadata(createdBy, now, now, null);
	    }

	    /** Used only by the persistence mapper to rehydrate from stored values. */
	    public static AuditMetadata rehydrate(UUID createdBy, LocalDateTime createdAt, LocalDateTime updatedAt, UUID updatedBy) {
	        return new AuditMetadata(createdBy, createdAt, updatedAt, updatedBy);
	    }

	    public void update(UUID userId) {
	        this.updatedBy = Objects.requireNonNull(userId, "User ID must not be null");
	        this.updatedAt = LocalDateTime.now();
	    }

//	    public LocalDateTime createdAt() { return createdAt; }
//	    public LocalDateTime updatedAt() { return updatedAt; }
//	    public UUID createdBy() { return createdBy; }
//	    public UUID updatedBy() { return updatedBy; }

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public UUID getCreatedBy() {
			return createdBy;
		}

		public UUID getUpdatedBy() {
			return updatedBy;
		}
	
	

}