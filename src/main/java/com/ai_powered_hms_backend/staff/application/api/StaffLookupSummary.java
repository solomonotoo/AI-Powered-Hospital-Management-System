package com.ai_powered_hms_backend.staff.application.api;

import java.util.UUID;
//to get one staff member's basic info (name, role, active) for login/JWT-claim purposes. Per-record, not aggregate.
public record StaffLookupSummary(UUID staffId, String fullName, String role, boolean canAuthenticate) {

}
