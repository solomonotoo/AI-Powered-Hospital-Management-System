package com.ai_powered_hms_backend.identity.application.command;

//the actual login
public record AuthenticationCommand(String email,String rawPassword) {

}
