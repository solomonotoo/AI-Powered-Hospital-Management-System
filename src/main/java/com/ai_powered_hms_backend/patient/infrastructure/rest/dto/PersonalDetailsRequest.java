package com.ai_powered_hms_backend.patient.infrastructure.rest.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

//request dto
public record PersonalDetailsRequest(
		@NotBlank String firstName,
		@NotBlank String lastName,
		String maidenName, //optional
		String preferredName, //optional
		@NotNull String gender,
		@NotNull String maritalStatus,
		@NotNull @Past LocalDate dateOfBirth,
		@NotNull String religion,
		@NotBlank String nationality,
		@NotBlank String ethnicity,
		@NotBlank String occupation
		) {

}
