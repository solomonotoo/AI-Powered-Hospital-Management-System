package com.ai_powered_hms_backend.shared_kernel.exceptions;

public class ConcurrencyConflictException extends DomainException {
	 public ConcurrencyConflictException(String entityType, Object id) {
	        super("CONCURRENCY_CONFLICT",
	            entityType + " with id " + id + " was modified concurrently — reload and retry");
	    }
}
