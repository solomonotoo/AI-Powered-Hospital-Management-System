//package com.hms_application.patient.domain.valueobjects;
//
//import com.hms_application.patient.domain.enums.Relationship;
//import com.hms_application.shared_kernel.valueobjects.Address;
//import com.hms_application.shared_kernel.valueobjects.PhoneNumber;
//
//import jakarta.persistence.AttributeOverride;
//import jakarta.persistence.AttributeOverrides;
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
///**
// * Immutable value object representing an emergency contact / next of kin.
// * Composed of the other value objects in this package (nested embeddables).
// *
// * Note: Hibernate supports nested @Embeddable records, but column names for
// * the inner Address/PhoneNumber fields will collide with any other embedded
// * Address/PhoneNumber on the same entity unless overridden — hence the
// * @AttributeOverrides below. Adjust column names to match your schema.
// */
////@Embeddable
//public record NextOfKinOld(
//
//        @NotBlank(message = "Next of kin name is required")
//        @Column(name = "kin_full_name")
//        String fullName,
//
//        @NotNull(message = "Relationship is required")
//        Relationship relationship,
//
//        @Valid
//        @AttributeOverrides({
//               // @AttributeOverride(name = "countryCode", column = @Column(name = "kin_phone_country_code")),
//                @AttributeOverride(name = "value", column = @Column(name = "kin_phone_number"))
//        })
//        PhoneNumber phoneNumber,
//
//        @Valid 
//        @AttributeOverrides({
//            @AttributeOverride(name = "line1", column = @Column(name = "kin_address_line1")),
//            @AttributeOverride(name = "line2", column = @Column(name = "kin_address_line2")),
//            @AttributeOverride(name = "street", column = @Column(name = "kin_street")),
//            @AttributeOverride(name = "city", column = @Column(name = "kin_city")),
//            @AttributeOverride(name = "state", column = @Column(name = "kin_state")),
//            @AttributeOverride(name = "postalCode", column = @Column(name = "kin_postal_code")),
//            @AttributeOverride(name = "country", column = @Column(name = "kin_country"))
//        }) 
//        Address address
//) {
//    public NextOfKin {
//        fullName = fullName == null ? null : fullName.trim();
//    }
//
//   
//}
//
