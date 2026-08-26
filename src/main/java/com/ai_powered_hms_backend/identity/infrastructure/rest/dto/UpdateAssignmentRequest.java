package com.ai_powered_hms_backend.identity.infrastructure.rest.dto;

import java.time.LocalDateTime;

public record UpdateAssignmentRequest(LocalDateTime expiresAt) {

}
