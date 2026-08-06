package com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

//NB this class will replace EmailEmbeddale.java and EmailMapper.java

//AttributeConverter -A class that implements this interface can be used to convert entity attribute 
//state into database column representation and back again.

@Converter
public class EmailConverter implements AttributeConverter<Email, String>{
	@Override
	public String convertToDatabaseColumn(Email attribute) {
		// TODO Auto-generated method stub
		return attribute == null ? null : attribute.getValue();
	}

	@Override
	public Email convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		return dbData == null ? null : new Email(dbData);
	}
	
}


