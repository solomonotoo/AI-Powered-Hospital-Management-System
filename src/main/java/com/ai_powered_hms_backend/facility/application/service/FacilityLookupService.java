package com.ai_powered_hms_backend.facility.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ai_powered_hms_backend.facility.application.api.FacilityLookup;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;

/**
 * facility module implements it internally (this is where Facility domain model is 
 * touched — legally, since it's within facility  thus ...facility.application.api)
 */

@Service
public class FacilityLookupService implements FacilityLookup{

	private final FacilityRepository facilityRepository;
	
	public FacilityLookupService(FacilityRepository facilityRepository) {
		this.facilityRepository = facilityRepository;
	}
	
	@Override
	public FacilitySummary getById(UUID facilityId) {
		
		//find or retrieve facility information by id
		Facility facility = facilityRepository.findById(FacilityId.of(facilityId))
				.orElseThrow(() -> new IllegalArgumentException(
                        "No facility found with id " + facilityId));
		
		//create a facility summary from the facility information retrieved
		return new FacilitySummary(
				facility.facilityId().value(),
				facility.code().value(),
				facility.name()
				);
	}

}
