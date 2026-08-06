package com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.PersonName;

//converts PersonName to PersonNameEmbeddable and vice versa
public record PersonNameMapper() {

	public static PersonNameEmbeddable toEmbeddable(PersonName domain) {
		if(domain == null) return null;
		return new PersonNameEmbeddable(domain.firstName(),domain.lastName(),domain.maidenName(),domain.preferredName());
	}
	
	public static PersonName toDomain(PersonNameEmbeddable embeddable) {
		if(embeddable == null) return null;
		return new PersonName(embeddable.getFirstName(), embeddable.getLastName(), embeddable.getMaidenName(), embeddable.getPreferredName());
		
	}
}
