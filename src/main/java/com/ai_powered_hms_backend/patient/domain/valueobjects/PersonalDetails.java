package com.ai_powered_hms_backend.patient.domain.valueobjects;

import com.ai_powered_hms_backend.patient.domain.enums.Religion;
import com.ai_powered_hms_backend.shared_kernel.enums.Gender;
import com.ai_powered_hms_backend.shared_kernel.enums.MaritalStatus;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.PersonName;

public record PersonalDetails(
		PersonName fullName,
		Gender gender,
		MaritalStatus maritalStatus,
		DateOfBirth dateOfBirth,
		Religion religion,
		String nationality,
		String ethnicity,
		String occupation
		
		) {

}
