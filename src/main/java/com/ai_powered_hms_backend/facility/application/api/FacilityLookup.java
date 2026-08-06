package com.ai_powered_hms_backend.facility.application.api;

import java.util.UUID;

//lookup service for retrieving basic information about a facility
//it specifies what operations are available, but not how they are implemented.
//thus information on facility id, code and name.

/**
 * NB facility module exposes a small public API package. This is implemented in
 * FacilityLookupService.java in com.hms_application.facility.application.service. Other modules like Patient Module can only touch or use
 * this file to access facility information
 */
public interface FacilityLookup {

	 FacilitySummary getById(UUID facilityId);

	 record FacilitySummary(UUID id, String code, String name) {}
}
