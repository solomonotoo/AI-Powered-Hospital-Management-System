package com.ai_powered_hms_backend.shared_kernel.exceptions;

public class BusinessRuleViolationException extends DomainException {
	 public BusinessRuleViolationException(String errorCode, String message) {
	        super(errorCode, message);
	    }

	    public BusinessRuleViolationException(String message) {
	        super("BUSINESS_RULE_VIOLATION", message);
	    }
}
