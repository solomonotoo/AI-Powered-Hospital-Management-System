package com.ai_powered_hms_backend.facility.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.facility.application.command.OnboardFacilityCommand;
import com.ai_powered_hms_backend.facility.application.port.in.OnboardFacilityUseCase;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;

@Service
public class OnboardFacilityService implements OnboardFacilityUseCase {

	private final FacilityRepository facilityRepository;
	
	public OnboardFacilityService(FacilityRepository facilityRepository) {
		this.facilityRepository = facilityRepository;
	}
	

	@Override
	@Transactional
	public FacilityId onboard(OnboardFacilityCommand command) {
		if(facilityRepository.existsByCode(command.code())) {
			throw new FacilityCodeAlreadyExistsException(command.code());
		}
		
		
		Facility facility = Facility.onboard(
				 command.code(),
	                command.name(),
	                command.type(),
	                command.location(),
	                command.contactPhone(),
	                command.contactEmail(),
	                command.createdBy()
				);
		
		facilityRepository.save(facility);
		
		return facility.facilityId();
	}
}
