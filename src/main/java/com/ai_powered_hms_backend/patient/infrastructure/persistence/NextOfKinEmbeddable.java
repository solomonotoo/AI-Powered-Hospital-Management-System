package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import com.ai_powered_hms_backend.patient.domain.enums.Relationship;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AddressEmbeddable;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.PhoneNumberConverter;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

//@Embeddable shadows 

/**
* Immutable value object representing an emergency contact / next of kin.
* Composed of the other value objects in this package (nested embeddables).
*
* Note: Hibernate supports nested @Embeddable records, but column names for
* the inner Address/PhoneNumber fields will collide with any other embedded
* Address/PhoneNumber on the same entity unless overridden — hence the
* @AttributeOverrides below. Adjust column names to match your schema.
*/

@Embeddable
public class NextOfKinEmbeddable {

	@Column(name = "kin_full_name", nullable = false, length = 100)
    private String fullName;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "kin_relationship", nullable = false, length = 30)
    private Relationship relationship;

    @Convert(converter = PhoneNumberConverter.class)
    @Column(name = "kin_phone_number",nullable = false,length = 20)
    private PhoneNumber phoneNumber;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "line1", column = @Column(name = "kin_address_line1")),
        @AttributeOverride(name = "line2", column = @Column(name = "kin_address_line2")),
        @AttributeOverride(name = "city", column = @Column(name = "kin_city")),
        @AttributeOverride(name = "state", column = @Column(name = "kin_state")),
        @AttributeOverride(name = "postalCode", column = @Column(name = "kin_postal_code")),
        @AttributeOverride(name = "country", column = @Column(name = "kin_country"))
    })
    private AddressEmbeddable address;
    
    protected NextOfKinEmbeddable() {
    	//required by JPA
    }
    
    public NextOfKinEmbeddable(String fullName, Relationship relationship, PhoneNumber phoneNumber, AddressEmbeddable address) {
        this.fullName = fullName;
        this.relationship = relationship;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getFullName() { return fullName; }
    public Relationship getRelationship() { return relationship; }
    public PhoneNumber getPhoneNumber() { return phoneNumber; }
    public AddressEmbeddable getAddress() { return address; }
}
