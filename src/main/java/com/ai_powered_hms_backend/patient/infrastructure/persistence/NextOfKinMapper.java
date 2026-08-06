package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import com.ai_powered_hms_backend.patient.domain.valueobjects.NextOfKin;
import com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence.AddressMapper;

// this convert NextOfKinEmbeddable to NextOfKin and vice versa
public class NextOfKinMapper {

	public static NextOfKinEmbeddable toEmbeddable(NextOfKin domain) {
		if(domain == null) return null;
		return new NextOfKinEmbeddable(
				
				domain.fullName(),
				domain.relationship(),
				domain.phoneNumber(),
				AddressMapper.toEmbeddable(domain.address())
				);
	}
	
	public static NextOfKin toDomain(NextOfKinEmbeddable embeddable) {
		if(embeddable == null) return null;
		return new NextOfKin(
				embeddable.getFullName(), 
				embeddable.getRelationship(), //relationship is an enum type
				embeddable.getPhoneNumber(), 
				AddressMapper.toDomain(embeddable.getAddress()));
		
	}
}
