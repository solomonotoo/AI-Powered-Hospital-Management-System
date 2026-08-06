package com.ai_powered_hms_backend.shared_kernel.valueobjects;

import com.ai_powered_hms_backend.shared_kernel.exceptions.InvalidNameException;


public record PersonName(String firstName, String lastName, String maidenName, String preferredName) {
    public PersonName {
        firstName = validate(firstName, "First name");
        lastName = validate(lastName, "Last name");
        maidenName = cleanseOptional(maidenName, "Maiden name");
        preferredName = cleanseOptional(preferredName, "Preferred name");
    }
    
 // Constructor for Staff Registration (No preferred name, optional maiden name)
    public PersonName(String firstName, String lastName,String maidenName) {
    	this(firstName, lastName, maidenName, null);
    }

    // Minimum Constructor (No preferred name, no maiden name)
    public PersonName(String firstName, String lastName) {
        this(firstName, lastName, null,null);
    }

    private static String cleanseOptional(String value, String field) {
    	if(value == null || value.isBlank()) {
    		return null;
    	}
    	return validate(value, field);
    }
    
    private static String validate(String value, String field) {
        if (value == null) throw new InvalidNameException(field + " cannot be null");
        String trimmed = value.trim();
        if (trimmed.isEmpty()) throw new InvalidNameException(field + " cannot be empty");
        if (trimmed.length() < 2 || trimmed.length() > 45)
            throw new InvalidNameException(field + " must be between 2 and 45 characters");
        return trimmed;
    }
}
