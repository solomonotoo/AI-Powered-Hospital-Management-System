package com.ai_powered_hms_backend.identity.application.port.out;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public record TokenClaims(StaffId staffId, String role, String tokenType) {

}
