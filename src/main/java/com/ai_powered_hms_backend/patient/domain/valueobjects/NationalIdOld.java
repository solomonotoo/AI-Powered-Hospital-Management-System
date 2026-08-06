//package com.hms_application.patient.domain.valueobjects;
//
//import jakarta.persistence.Embeddable;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//
///**
// * Immutable value object representing a government-issued identifier
// * (national ID card, passport, driver's license, etc.).
// */
////@Embeddable
//public record NationalIdOld(
//
//        @NotNull(message = "ID type is required")
//        @Enumerated(EnumType.STRING)
//        IdType idType,
//
//        @NotBlank(message = "ID number is required")
//        @Size(max = 40, message = "ID number must not exceed 40 characters")
//        String idNumber,
//
//        @NotBlank(message = "Issuing country is required")
//        @Size(max = 56, message = "Issuing country must not exceed 56 characters")
//        String issuingCountry
//) {
//    public NationalIdOld {
//        idNumber = idNumber == null ? null : idNumber.trim().toUpperCase();
//        issuingCountry = issuingCountry == null ? null : issuingCountry.trim();
//    }
//
//    public enum IdType {
//        NATIONAL_ID_CARD,
//        PASSPORT,
//        DRIVERS_LICENSE,
//        SOCIAL_SECURITY_NUMBER,
//        VOTER_ID
//    }
//
//    /** Masks all but the last 4 characters, useful for display/logging. */
//    public String masked() {
//        if (idNumber == null || idNumber.length() <= 4) {
//            return "****";
//        }
//        return "*".repeat(idNumber.length() - 4) + idNumber.substring(idNumber.length() - 4);
//    }
//}
