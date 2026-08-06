package com.ai_powered_hms_backend.staff.application.api;

import java.util.UUID;

public record StaffSummary(UUID staffId, String fullName, String role, boolean active) {

}
