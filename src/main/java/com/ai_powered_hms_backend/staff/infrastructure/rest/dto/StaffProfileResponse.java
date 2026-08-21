package com.ai_powered_hms_backend.staff.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record StaffProfileResponse( 
		 String staffId,
	        String employeeNumber,
	        //NB string fullName changed to firstName and lastName in order not to leak the domain
	        String firstName,
	        String lastName,
	        String role,
	        String specialisation,
	        String department,
	        String workEmail,
	        String phone,
	        String licenseNumber,
	        String qualifications,
	        LocalDate joiningDate,
	        LocalDate endDate,
	        String workingHours,
	        BigDecimal consultationFee,
	        String status,
	        String createdAt,
	        String updatedAt
		) {

}
