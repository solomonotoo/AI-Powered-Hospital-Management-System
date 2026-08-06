package com.ai_powered_hms_backend.patient.application.port.in;

import com.ai_powered_hms_backend.patient.application.command.RegisterPatientCommand;
import com.ai_powered_hms_backend.shared_kernel.ids.PatientId;

public interface RegisterPatientUseCase {
	PatientId register(RegisterPatientCommand command);
}
