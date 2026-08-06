package com.ai_powered_hms_backend.shared_kernel.base;

import java.time.Instant;

public abstract class Auditable {

	protected Instant createdAt;
	protected Instant updatedAt;
	
	protected Auditable() {
		this.createdAt = Instant.now();
		this.updatedAt = Instant.now();
	}
	
	protected void touch() {
		this.updatedAt = Instant.now();
	}
	
	public Instant getCreatedAt() {return createdAt;}
	public Instant getUpdatedAt() {return updatedAt;}
}
