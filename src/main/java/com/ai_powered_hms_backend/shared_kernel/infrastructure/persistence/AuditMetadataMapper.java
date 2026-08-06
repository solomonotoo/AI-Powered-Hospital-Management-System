package com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.AuditMetadata;

//converts AuditMetadata(domain) to AuditMetadataEmbeddable and v
//this is the only place both types are ever imported together
public class AuditMetadataMapper {

	
	public static AuditMetadataEmbeddable toEmbeddable(AuditMetadata domain) {
		return new AuditMetadataEmbeddable(
				domain.getCreatedAt(), 
				domain.getUpdatedAt(), 
				domain.getCreatedBy(),
				domain.getUpdatedBy());
	}
	
//	Object hydration refers to the process of transforming data from its raw state 
//	(such as database rows or JSON data) into fully populated Java objects.
	public static AuditMetadata toDomain(AuditMetadataEmbeddable embeddable) {
		return AuditMetadata.rehydrate(
				embeddable.getCreatedBy(),
                embeddable.getCreatedAt(),
                embeddable.getUpdatedAt(),
                embeddable.getUpdatedBy()
				);
	}

}
