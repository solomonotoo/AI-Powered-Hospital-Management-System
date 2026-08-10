package com.ai_powered_hms_backend.facility.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.facility.application.port.in.ChangeFacilityStatusUseCase;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.facility.domain.model.Facility;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;


@Service
public class ChangeFacilityStatusService implements ChangeFacilityStatusUseCase {

	private final FacilityRepository facilityRepository;
	
	public ChangeFacilityStatusService(FacilityRepository facilityRepository) {
		super();
		this.facilityRepository = facilityRepository;
	}

	@Override
	public void deactivate(FacilityId facilityId, UUID modifiedBy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void reactivate(FacilityId facilityId, UUID modifiedBy) {
		Facility facility = getOrThrow(facilityId);
		
	}

	
	
	private Facility getOrThrow(FacilityId id) {
		return facilityRepository.findById(id)
				.orElseThrow(() -> new FacilityNotFoundException("No facility found with id " + id.value()));
	}
}
