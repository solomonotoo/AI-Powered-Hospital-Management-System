package com.ai_powered_hms_backend.facility.application.service;

import org.springframework.stereotype.Service;

import com.ai_powered_hms_backend.facility.application.port.in.GetFacilitiesUseCase;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.facility.infrastructure.rest.dto.FacilitySummaryResponse;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;


@Service
public class GetFacilityService implements GetFacilitiesUseCase{

	private final FacilityRepository facilityRepository;
	
	public GetFacilityService(FacilityRepository facilityRepository) {
		super();
		this.facilityRepository = facilityRepository;
	}


	@Override
	public Facility getById(FacilityId id) {
		// TODO Auto-generated method stub
		return facilityRepository.findById(id)
				.orElseThrow(() -> new FacilityNotFoundException("No facility found"));
	}


//	@Override
//	public FacilitySummaryResponse getSummary() {
//		// TODO Auto-generated method stub
//		return null;
//	}


	


	

}
