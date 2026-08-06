package com.ai_powered_hms_backend.shared_kernel.exceptions;

import java.util.List;

public class ValidationException extends DomainException {

	private final List<String> violations;

    public ValidationException(List<String> violations) {
        super("VALIDATION_FAILED", "Validation failed: " + String.join("; ", violations));
        this.violations = List.copyOf(violations);
    }

    public List<String> violations() {
        return violations;
    }
}
