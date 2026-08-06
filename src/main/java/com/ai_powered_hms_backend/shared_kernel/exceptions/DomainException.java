package com.ai_powered_hms_backend.shared_kernel.exceptions;

public class DomainException extends RuntimeException {
	 private final String errorCode;

	    protected DomainException(String errorCode, String message) {
	        super(message);
	        this.errorCode = errorCode;
	    }

	    protected DomainException(String errorCode, String message, Throwable cause) {
	        super(message, cause);
	        this.errorCode = errorCode;
	    }

	    public String errorCode() {
	        return errorCode;
	    }
}
