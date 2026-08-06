package com.ai_powered_hms_backend.staff.application.api;

import java.util.UUID;

//Staff's public API (consumed by identity, one-directional)
// its implementation is done in StaffLookupService.java
public interface StaffLookup {
	StaffSummary getById(UUID staffId);
}
