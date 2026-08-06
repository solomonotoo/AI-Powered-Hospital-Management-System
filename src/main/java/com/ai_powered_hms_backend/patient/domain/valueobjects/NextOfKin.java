package com.ai_powered_hms_backend.patient.domain.valueobjects;

import java.util.Objects;

import com.ai_powered_hms_backend.patient.domain.enums.Relationship;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

public record NextOfKin(

        String fullName,
        Relationship relationship,
        PhoneNumber phoneNumber,
        Address address
) {
    public NextOfKin {
    	fullName = requireNotBlank(fullName, "Next of kin full name");
        relationship = Objects.requireNonNull(relationship, "Relationship is required");
        phoneNumber = Objects.requireNonNull(phoneNumber, "Next of kin phone number is required");
    }

    private static String requireNotBlank(String value, String fieldName) {
    	Objects.requireNonNull(value,fieldName + " cannot be null");
    	String trimmed = value.trim();
    	if(trimmed.isEmpty()) {
    		throw new IllegalArgumentException(fieldName + " cannot be blank ");
    	}
    	return trimmed;
    }
   
}
