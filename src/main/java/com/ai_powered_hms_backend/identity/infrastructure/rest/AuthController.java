package com.ai_powered_hms_backend.identity.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_powered_hms_backend.identity.application.command.AuthenticationCommand;
import com.ai_powered_hms_backend.identity.application.port.in.AuthenticateUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthenticateUseCase authenticateUseCase;

	public AuthController(AuthenticateUseCase authenticateUseCase) {
		this.authenticateUseCase = authenticateUseCase;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		var result = authenticateUseCase.authentication(new AuthenticationCommand(request.email(), request.password()));

		return ResponseEntity.ok(new LoginResponse(result.token().token(), result.token().expireAt().toString(),
				result.staffId(), result.fullName(), result.role(), result.mustChangePassword()));
	}

}
