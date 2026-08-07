package com.ai_powered_hms_backend.facility.application.service;

import org.springframework.stereotype.Service;

import com.ai_powered_hms_backend.facility.application.command.UpdateFacilityCommand;
import com.ai_powered_hms_backend.facility.application.port.in.UpdateFacilityUseCase;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.facility.domain.model.Facility;


@Service
public class UpdateFacilityService implements UpdateFacilityUseCase{

	private final FacilityRepository facilityRepository;
	
	public UpdateFacilityService(FacilityRepository facilityRepository) {
		super();
		this.facilityRepository = facilityRepository;
	}

	@Override
	public void update(UpdateFacilityCommand command) {
		// get facility by id
		Facility facility = facilityRepository.findById(command.facilityId())
				.orElseThrow(() -> new FacilityNotFoundException("No faciltity found with id " + command.facilityId().value()));
		
		//update fields
		facility.rename(command.name(), command.modifiedBy());
		facility.reclassify(command.type(), command.modifiedBy());
		facility.relocate(command.location(), command.modifiedBy());
		facility.updateContactPhone(command.contactPhone(), command.modifiedBy());
		facility.updateContactEmail(command.contactEmail(), command.modifiedBy());
		
		facilityRepository.save(facility);
	}

//	Note: FacilityCode is deliberately not updatable here — since it's the MRN prefix 
//	baked into every existing patient's MRN at that facility, changing it after patients
//	exist would desynchronize historical MRNs from the facility's current code. If a code
//	genuinely needs correcting, that should be a rare, explicit, carefully-audited 
//	operation — not part of routine editing. Flag if you disagree.
}
