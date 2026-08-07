package com.ai_powered_hms_backend.facility.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.facility.application.port.in.ListFacilitiesUseCase;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityPage;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.facility.application.query.ListFacilitiesQuery;

@Service
public class ListFacilitiesService implements ListFacilitiesUseCase{

	private final FacilityRepository facilityRepository;
	
	public ListFacilitiesService(FacilityRepository facilityRepository) {
		super();
		this.facilityRepository = facilityRepository;
	}

	@Override
	@Transactional
	public FacilityPage list(ListFacilitiesQuery query) {
		// TODO Auto-generated method stub
		return facilityRepository.findAll(query.status(), query.type(), query.page(), query.size());
	}

}
