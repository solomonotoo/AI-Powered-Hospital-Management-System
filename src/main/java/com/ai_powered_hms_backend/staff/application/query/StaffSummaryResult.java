package com.ai_powered_hms_backend.staff.application.query;




public record StaffSummaryResult(
		long totalStaff,
        long activeStaff,
        long inactiveStaff,
        long onDuty,
        long onLeave) {

}
