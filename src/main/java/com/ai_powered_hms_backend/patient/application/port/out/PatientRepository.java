package com.ai_powered_hms_backend.patient.application.port.out;

import java.util.Optional;

import com.ai_powered_hms_backend.patient.domain.model.Patient;
import com.ai_powered_hms_backend.shared_kernel.ids.PatientId;

//Ports (interfaces the application layer depends on, implemented by infrastructure)
public interface PatientRepository {

	void save(Patient patient);
	Optional<Patient> findBy(PatientId id);
}
