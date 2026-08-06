package com.ai_powered_hms_backend.identity.infrastructure.persistence;

import com.ai_powered_hms_backend.identity.domain.valueobjects.HashedPassword;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class HashedPasswordConverter implements AttributeConverter<HashedPassword, String> {

	@Override
	public String convertToDatabaseColumn(HashedPassword attribute) {
		// TODO Auto-generated method stub
		return attribute == null ? null : attribute.value();
	}

	@Override
	public HashedPassword convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		return dbData == null ? null : new HashedPassword(dbData);
	}

}
