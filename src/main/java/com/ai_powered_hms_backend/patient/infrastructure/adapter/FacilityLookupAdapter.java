package com.ai_powered_hms_backend.patient.infrastructure.adapter;


import java.util.UUID;

import org.springframework.stereotype.Component;

import com.ai_powered_hms_backend.facility.application.api.FacilityLookup;
import com.ai_powered_hms_backend.facility.application.port.out.FacilityRepository;
import com.ai_powered_hms_backend.patient.application.port.out.FacilityLookupPort;
import com.ai_powered_hms_backend.shared_kernel.ids.FacilityId;

// patient's adapter now only touches or access facility.api which was exposed through FacilityLookup.java in Facility Module,
//never facility.domain
@Component
public class FacilityLookupAdapter implements FacilityLookupPort{

	private final FacilityLookup facilityLookup;
	
	public FacilityLookupAdapter(FacilityLookup facilityLookup) {
		this.facilityLookup = facilityLookup;
	}

	@Override
	public FacilitySummary getById(UUID facilityId) {
		FacilityLookup.FacilitySummary summary = facilityLookup.getById(facilityId);
		return new FacilitySummary(summary.id(),summary.code(),summary.name());
	}
	
	
}
