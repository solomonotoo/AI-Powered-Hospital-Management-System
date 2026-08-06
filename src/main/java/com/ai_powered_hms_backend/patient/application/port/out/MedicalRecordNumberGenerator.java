package com.ai_powered_hms_backend.patient.application.port.out;

import com.ai_powered_hms_backend.shared_kernel.valueobjects.MRN;

public interface MedicalRecordNumberGenerator {
	MRN generateFor(String facilityCode);
}
