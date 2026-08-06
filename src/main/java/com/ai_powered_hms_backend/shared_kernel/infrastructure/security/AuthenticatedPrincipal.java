package com.ai_powered_hms_backend.shared_kernel.infrastructure.security;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

public record AuthenticatedPrincipal(StaffId staffId,String role) {

}
