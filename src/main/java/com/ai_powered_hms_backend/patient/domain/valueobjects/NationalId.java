package com.ai_powered_hms_backend.patient.domain.valueobjects;

import java.util.Objects;

import com.ai_powered_hms_backend.patient.domain.enums.IdType;

public record NationalId(

        IdType idType,
        String idNumber,
        String issuingCountry
) {
    public NationalId {
    	Objects.requireNonNull(idType, "ID type is required");
    	idNumber = requireNotBlank(idNumber, "ID number");
    	issuingCountry = requireNotBlank(issuingCountry,"Issuing country");
    	
    	if (idNumber.length() > 40) {
            throw new IllegalArgumentException("ID number must not exceed 40 characters");
        }
        if (issuingCountry.length() > 56) {
            throw new IllegalArgumentException("Issuing country must not exceed 56 characters");
        }
        idNumber = idNumber.toUpperCase();
    }
    
    public static String requireNotBlank(String value, String fieldName) {
    	Objects.requireNonNull(value, fieldName + " cannot be null");
    	String trimmed = value.trim();
    	
    	if(trimmed.isEmpty()) {
    		throw new IllegalArgumentException(fieldName + " cannot be blank");
    	}
    	
    	return trimmed;
    }

  

    /** Masks all but the last 4 characters, useful for display/logging. */
    public String masked() {
        if (idNumber == null || idNumber.length() <= 4) {
            return "****";
        }
        return "*".repeat(idNumber.length() - 4) + idNumber.substring(idNumber.length() - 4);
    }
}
