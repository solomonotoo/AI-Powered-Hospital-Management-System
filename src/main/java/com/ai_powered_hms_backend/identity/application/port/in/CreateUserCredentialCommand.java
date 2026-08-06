package com.ai_powered_hms_backend.identity.application.port.in;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;

public record CreateUserCredentialCommand(StaffId staffId, Email loginEmail, String rawPassword) {

}
