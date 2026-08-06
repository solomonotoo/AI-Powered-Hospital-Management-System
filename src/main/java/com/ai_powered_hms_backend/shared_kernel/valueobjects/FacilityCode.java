package com.ai_powered_hms_backend.shared_kernel.valueobjects;

import java.util.Objects;

// A real value object (not a raw String), since it has strict format rules and is reused as the MRN prefix elsewhere:
public record FacilityCode(String value) {

	public FacilityCode{
		Objects.requireNonNull(value,"Facility code must not be null");
		String normalized = value.trim().toUpperCase();
		
		if(!normalized.matches("^[A-Z0-9]{2,10}$")) {
			throw new IllegalArgumentException(  "Facility code must be 2-10 alphanumeric characters");
		}
		value = normalized;
	}
	
	 @Override
	    public String toString() {
	        return value;
	    }
}
