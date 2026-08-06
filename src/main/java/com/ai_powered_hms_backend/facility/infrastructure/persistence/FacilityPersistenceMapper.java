package com.ai_powered_hms_backend.facility.infrastructure.persistence;

import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AddressMapper;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AuditMetadataMapper;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Address;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.FacilityCode;

//Mapper (domain ↔ entity — this is the translation seam, keeps JPA out of the domain entirely):
public class FacilityPersistenceMapper {

	//convert domain objects to Jpa entity object
	public static FacilityJpaEntity toEntity(Facility facility) {
		return new FacilityJpaEntity(
				facility.facilityId().value(),
				facility.code().value(),
				facility.name(),
				facility.type(),
				AddressMapper.toEmbeddable(facility.location()),
				facility.contactPhone(),
				facility.contactEmail(),
				facility.status(),
				AuditMetadataMapper.toEmbeddable(facility.audit())
				
				);
	}
	
	//converts jpa entity to domain object 
	public static Facility toDomain(FacilityJpaEntity entity) {
		System.out.println("Facility ID: " + entity.getId());
	    System.out.println("Raw location: " + entity.getLocation());

	    if (entity.getLocation() != null) {
	        System.out.println("Line1: " + entity.getLocation().getLine1());
	        System.out.println("City: " + entity.getLocation().getCity());
	        System.out.println("Country: " + entity.getLocation().getCountry());
	    }

	    Address address = AddressMapper.toDomain(entity.getLocation());

	    System.out.println("Mapped domain address: " + address);
		return Facility.reconstitute(
				FacilityId.of(entity.getId()),
				new FacilityCode(entity.getCode()), 
				entity.getName(), 
				entity.getType(),
				AddressMapper.toDomain(entity.getLocation()), 
				entity.getContactPhone(), 
				entity.getContactEmail(), 
				entity.getStatus(), 
				AuditMetadataMapper.toDomain(entity.getAudit())
				);
	}
}
