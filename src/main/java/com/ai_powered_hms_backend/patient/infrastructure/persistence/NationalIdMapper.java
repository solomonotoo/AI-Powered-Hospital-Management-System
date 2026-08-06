package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import com.ai_powered_hms_backend.patient.domain.valueobjects.NationalId;

//convert NationalIdEmbeddable to NationalId and vice versa

public class NationalIdMapper {

	public static NationalIdEmbeddable toEmbeddable(NationalId domain) {
		if(domain == null) return null;
		return new NationalIdEmbeddable(domain.idType(),domain.idNumber(),domain.issuingCountry());
	}
	
	public static NationalId toDomain(NationalIdEmbeddable embeddable) {
		if(embeddable == null) return null;
		return new NationalId(embeddable.getIdType(), embeddable.getIdNumber(), embeddable.getIssuingCountry());
	}
}
