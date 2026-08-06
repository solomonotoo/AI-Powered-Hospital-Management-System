package com.ai_powered_hms_backend.patient.infrastructure.persistence;

import java.time.LocalDate;

import com.ai_powered_hms_backend.patient.domain.valueobjects.DateOfBirth;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter
public class DateOfBirthConverter implements AttributeConverter<DateOfBirth, LocalDate>{

	@Override
	public LocalDate convertToDatabaseColumn(DateOfBirth attribute) {
		// TODO Auto-generated method stub
		return attribute == null ? null : attribute.value();
	}

	@Override
	public DateOfBirth convertToEntityAttribute(LocalDate dbData) {
		// TODO Auto-generated method stub
		return dbData == null ? null : DateOfBirth.of(dbData);
	}
	

}
