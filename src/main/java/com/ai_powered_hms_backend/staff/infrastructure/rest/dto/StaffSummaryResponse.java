package com.ai_powered_hms_backend.staff.infrastructure.rest.dto;

public record StaffSummaryResponse(
		long totalStaff,
        long activeStaff,
        long inactiveStaff,
        long onDuty,
        long onLeave) {

}
