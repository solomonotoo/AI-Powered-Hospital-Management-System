package com.ai_powered_hms_backend.patient.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.patient.application.command.RegisterPatientCommand;
import com.ai_powered_hms_backend.patient.application.port.in.RegisterPatientUseCase;
import com.ai_powered_hms_backend.patient.application.port.out.FacilityLookupPort;
import com.ai_powered_hms_backend.patient.application.port.out.FacilityLookupPort.FacilitySummary;
import com.ai_powered_hms_backend.patient.application.port.out.MedicalRecordNumberGenerator;
import com.ai_powered_hms_backend.patient.application.port.out.PatientRepository;
import com.ai_powered_hms_backend.patient.domain.model.Patient;
import com.ai_powered_hms_backend.shared_kernel.ids.PatientId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.MRN;

@Service
public class RegisterPatientService implements RegisterPatientUseCase{

	private final FacilityLookupPort facilityLookupPort;
	private final MedicalRecordNumberGenerator mrnGenerator;
	private final PatientRepository patientRepository;
	
	
	
	
	public RegisterPatientService(FacilityLookupPort facilityLookupPort, MedicalRecordNumberGenerator mrnGenerator,
			PatientRepository patientRepository) {
		super();
		this.facilityLookupPort = facilityLookupPort;
		this.mrnGenerator = mrnGenerator;
		this.patientRepository = patientRepository;
	}




	@Override
	@Transactional
	public PatientId register(RegisterPatientCommand command) {
		FacilitySummary facility = facilityLookupPort.getById(command.facilityId());
		MRN mrn = mrnGenerator.generateFor(facility.code());
		Patient patient = Patient.register(
				command.personalDetails(),
				command.medicalDetails(), 
				command.contactDetails(), 
				command.nextOfKin(),
				command.insuranceInformation(), 
				command.patientType(), 
				command.preferredLanguage(),
				mrn,
				command.registeredBy());

		patientRepository.save(patient);
		
		return patient.patientId();
	}

}
