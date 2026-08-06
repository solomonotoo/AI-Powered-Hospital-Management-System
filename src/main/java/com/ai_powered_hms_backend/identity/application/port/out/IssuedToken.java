package com.ai_powered_hms_backend.identity.application.port.out;

import java.time.Instant;

public record IssuedToken(String token, Instant expireAt) {

}
