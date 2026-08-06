package com.ai_powered_hms_backend.patient.application.port.out;

import java.util.UUID;

//
//Note FacilityLookupPort returns a small FacilitySummary, not a Facility aggregate from another bounded 
//context — this keeps the patient module from depending on the facility module's internal 
//domain model. If facilities live in the same module/monolith, this is still good 
//practice for a clean seam if you ever split it out as a separate service.

//this is implemented in FacilityLookupAdaptor.java in com.hms_application.patient.infrastructure.adapter;

public interface FacilityLookupPort {

	FacilitySummary getById(UUID facilityId);
	record FacilitySummary(UUID id, String code, String name) {}
}
