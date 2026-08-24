package com.ai_powered_hms_backend.identity.application.port.out;

import java.util.UUID;

import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

//without session 
//public record TokenClaims(StaffId staffId, String role, String tokenType) {}

// with session id
public record TokenClaims(StaffId staffId, String role, String tokenType, UUID sessionId) {}