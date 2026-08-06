package com.ai_powered_hms_backend.patient.domain.valueobjects;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Immutable value object representing a person's health insurance details.
 * 
 * Recall this whole VO is optional at the Patient level (self-pay patients), 
 * so nullability is handled at the aggregate boundary — this VO itself still 
 * requires its own internal fields to be valid when one is constructed at all.
 */

public record InsuranceInformation(
        String provider,
        String policyNumber,

        String groupNumber,
        LocalDate coverageStartDate,

        LocalDate expirationDate
) {
    public InsuranceInformation {
        provider = provider == null ? null : provider.trim();
        policyNumber = policyNumber == null ? null : policyNumber.trim();
        groupNumber = groupNumber == null ? null : groupNumber.trim();

        Objects.requireNonNull(coverageStartDate, "Coverage start date is required");

        if (expirationDate != null   && expirationDate.isBefore(coverageStartDate)) {
            throw new IllegalArgumentException("Expiration date cannot be before coverage start date");
        }
    }
    
    public static String requireNotBlank(String value, String fieldName) {
    	Objects.requireNonNull(value, fieldName + " cannot be null");
    	String trimmed = value.trim();
    	if(trimmed.isEmpty()) {
    		throw new IllegalArgumentException(fieldName + " cannot be blank");
    	}
    	
    	return trimmed;
    }

    /** True if coverage has started and has not yet expired, as of today. */
    public boolean isActive() {
        LocalDate today = LocalDate.now();
        boolean started = !today.isBefore(coverageStartDate);
        boolean notExpired = expirationDate == null || !today.isAfter(expirationDate);
        return started && notExpired;
    }
}
