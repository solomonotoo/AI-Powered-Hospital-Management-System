package com.ai_powered_hms_backend.shared_kernel.infrastructure.persistence;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

//Specifies that the annotated class is a converter and defines its scope. A converter class must be annotated with the Converter annotation or defined in the object/relational mapping descriptor as a converter.
//If the autoApply element is specified as true, the persistence provider must automatically apply the converter to all mapped attributes of the specified target type for all entities in the persistence unit except for attributes for which conversion is overridden by means of the Convert annotation (or XML equivalent).
//In determining whether a converter is applicable to an attribute, the provider must treat primitive types and wrapper types as equivalent.
//Note that Id attributes, version attributes, relationship attributes, and attributes explicitly annotated as Enumerated or Temporal (or designated as such via XML) will not be converted.
//Note that if autoApply is true, the Convert annotation may be used to override or disable auto-apply conversion on a per-attribute basis.
//If autoApply is false, only those attributes of the target type for which the Convert annotation (or corresponding XML element) has been specified will be converted.
//If there is more than one converter defined for the same target type, the Convert annotation should be used to explicitly specify which converter to use

//this class will replace PhoneNumberMapper and PhoneNumberEmbeddable

@Converter
public class PhoneNumberConverter implements AttributeConverter<PhoneNumber, String>{

	@Override
	public String convertToDatabaseColumn(PhoneNumber attribute) {
		// TODO Auto-generated method stub
		return attribute == null ? null : attribute.value();
	}

	@Override
	public PhoneNumber convertToEntityAttribute(String dbData) {
		// TODO Auto-generated method stub
		return dbData == null ? null : new PhoneNumber(dbData);
	}

}
