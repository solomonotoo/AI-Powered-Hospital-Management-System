package com.ai_powered_hms_backend.patient.domain.valueobjects;

import com.ai_powered_hms_backend.patient.domain.enums.Genotype;
import com.ai_powered_hms_backend.shared_kernel.enums.BloodGroup;

public record MedicalDetails(
		BloodGroup bloodGroup,
		Genotype genoType,
		NationalId nationalId
		) {

}
