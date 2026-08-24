package com.ai_powered_hms_backend.identity.application.port.out;

import java.time.Instant;
import java.util.UUID;

//without session
//public record IssuedToken(String token, Instant expireAt) {}


//with Session
public record IssuedToken(String token, Instant expireAt,UUID sessionId) {
}
