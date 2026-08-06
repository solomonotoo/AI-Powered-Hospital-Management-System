package com.ai_powered_hms_backend.staff.application.command;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PersonName;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PhoneNumber;
import com.ai_powered_hms_backend.staff.domain.enums.StaffRole;
import com.ai_powered_hms_backend.staff.domain.valueobjects.EmployeeNumber;

//The command is a simple data carrier that contains everything needed to
//perform the use case.
//no business logic
//no validation beyond simple structural checks
//just input data

//NB the parameter of this record must match that of the factory method 
//onboard in StaffProfile.java

public record OnboardStaffCommand(
		EmployeeNumber employeeNumber,
		PersonName fullName, 
		StaffRole role,
		String specialisation, 
		String department, 
		Email workEmail, 
		PhoneNumber phone, 
		String licenseNumber,
		String qualifications,
		LocalDate joiningDate,
		String workingHours,
		BigDecimal consultationFee,
		UUID createdBy	
		) {

}

