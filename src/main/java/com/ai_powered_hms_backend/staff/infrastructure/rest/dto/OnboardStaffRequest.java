package com.ai_powered_hms_backend.staff.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//this is a request dto and should be mapped to OnboardStaffCommand
public record OnboardStaffRequest(
		@NotBlank String employeeNumbe,
		@NotBlank String firstName,
		@NotBlank String lastname,
		@NotBlank String role,
		String specialisation,
		String department,
		@NotBlank @Email String workEmail,
		String phone,
		String licenseNumber,
		String qualifications,
		@NotNull LocalDate joiningDate,
		String workingHours,
		BigDecimal consultationFee
		
		) {

}
