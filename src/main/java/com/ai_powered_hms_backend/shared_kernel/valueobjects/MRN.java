package com.ai_powered_hms_backend.shared_kernel.valueobjects;

/**
 * Immutable value object representing a Medical Record Number (MRN),
 * scoped to the issuing facility to avoid collisions across facilities.
 */
//@Embeddable
public record MRN(

//        @NotBlank(message = "Facility code is required")
//        @Pattern(regexp = "^[A-Za-z0-9]{2,10}$", message = "Facility code must be 2-10 alphanumeric characters")
        String facilityCode,

//        @NotBlank(message = "MRN value is required")
//        @Pattern(regexp = "^[A-Za-z0-9\\-]{4,20}$", message = "Invalid MRN format")
        String value
) {
    public MRN {
        facilityCode = facilityCode == null ? null : facilityCode.trim().toUpperCase();
        value = value == null ? null : value.trim().toUpperCase();
        
        if (facilityCode == null || !facilityCode.matches("^[A-Z0-9]{2,10}$")) {
            throw new IllegalArgumentException("Facility code must be 2-10 alphanumeric characters");
        }
        if (value == null || !value.matches("^[A-Z0-9\\-]{4,20}$")) {
            throw new IllegalArgumentException("Invalid MRN format");
        }
    }

    /** Fully-qualified MRN, e.g. "GENHOSP-000123". */
    public String fullNumber() {
        return facilityCode + "-" + value;
    }

    @Override
    public String toString() {
        return fullNumber();
    }
}
