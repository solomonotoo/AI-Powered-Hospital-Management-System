package com.ai_powered_hms_backend.facility.infrastructure.persistence;

import java.util.UUID;

import com.ai_powered_hms_backend.facility.domain.eums.FacilityStatus;
import com.ai_powered_hms_backend.facility.domain.eums.FacilityType;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AddressEmbeddable;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataEmbeddable;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.EmailConverter;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.PhoneNumberConverter;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


//The @AttributeOverride(name = "value", column = @Column(name = "contact_phone")) 
//is required here specifically because PhoneNumberEmbeddable and EmailEmbeddable both 
//have a plain unqualified column named implicitly from the field (value) — without the
//override, Hibernate would try to map both to a column literally named value, causing 
//a collision if you ever embed two of the same type in one entity, or an unclear column 
//name in the schema. Same override pattern will be needed on Patient's entity for phoneNumber
//vs alternatePhone (both PhoneNumberEmbeddable).

@Entity
@Table(name = "facilities")
public class FacilityJpaEntity {

	@Id
	private UUID id;
	
	@Column(name = "facility_code", nullable = false, unique = true, length = 10)
	private String code;
	
	@Column(name = "name", nullable = false,length = 200)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false, length = 30)
	private FacilityType type;
	
	
    @Embedded
    private AddressEmbeddable location;

    @Convert(converter = PhoneNumberConverter.class)
	@Column(name = "contact_phone", nullable = false, length = 20)
    private PhoneNumber contactPhone;

    @Convert(converter = EmailConverter.class)
	@Column(name = "contact_email",length = 150)
    private Email contactEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private FacilityStatus status;

    @Embedded
    private AuditMetadataEmbeddable audit;
    
    protected FacilityJpaEntity() {//needed by JPA
    		
    }
    	
	 public FacilityJpaEntity(
	            UUID id, String code, String name, FacilityType type,
	            AddressEmbeddable location, PhoneNumber contactPhone,
	            Email contactEmail, FacilityStatus status, AuditMetadataEmbeddable audit
	    ) {
	        this.id = id;
	        this.code = code;
	        this.name = name;
	        this.type = type;
	        this.location = location;
	        this.contactPhone = contactPhone;
	        this.contactEmail = contactEmail;
	        this.status = status;
	        this.audit = audit;
	    }

	 public UUID getId() {
		 return id;
	 }

	 public String getCode() {
		 return code;
	 }

	 public String getName() {
		 return name;
	 }

	 public FacilityType getType() {
		 return type;
	 }

	 public AddressEmbeddable getLocation() {
		 return location;
	 }

	 public PhoneNumber getContactPhone() {
		 return contactPhone;
	 }

	 public Email getContactEmail() {
		 return contactEmail;
	 }

	 public FacilityStatus getStatus() {
		 return status;
	 }

	 public AuditMetadataEmbeddable getAudit() {
		 return audit;
	 }
    	
    	
    	
    	
    	
    	
   
}
