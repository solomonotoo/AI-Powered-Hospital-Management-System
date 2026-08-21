package com.ai_powered_hms_backend.facility.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.facility.application.port.in.GetFacilitySummaryUseCase;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.facility.application.query.FacilitySummaryResult;


@Service
public class GetFacilitySummaryService implements GetFacilitySummaryUseCase{

	private final FacilityRepository facilityRepository;
	
	public GetFacilitySummaryService(FacilityRepository facilityRepository) {
		super();
		this.facilityRepository = facilityRepository;
	}


	@Override
	@Transactional
	public FacilitySummaryResult getSummary() {
		// TODO Auto-generated method stub
		return facilityRepository.getSummary();
	}

}
