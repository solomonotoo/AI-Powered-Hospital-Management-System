package com.ai_powered_hms_backend.identity.infrastructure.rest;



import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_powered_hms_backend.identity.application.command.AuthenticationCommand;
import com.ai_powered_hms_backend.identity.application.command.RefreshTokenCommand;
import com.ai_powered_hms_backend.identity.application.port.in.AuthenticateUseCase;
import com.ai_powered_hms_backend.identity.application.port.in.RefreshTokenUseCase;
import com.ai_powered_hms_backend.identity.application.service.SessionQueryService;
import com.ai_powered_hms_backend.identity.infrastructure.rest.dto.SessionResponse;
import com.ai_powered_hms_backend.identity.infrastructure.rest.mapper.SessionResponseMapper;
import com.ai_powered_hms_backend.shared_kernel.ids.SessionId;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthenticateUseCase authenticateUseCase;
	private final RefreshTokenUseCase refreshTokenUseCase;
	
	public AuthController(AuthenticateUseCase authenticateUseCase, RefreshTokenUseCase refreshTokenUseCase) {
		super();
		this.authenticateUseCase = authenticateUseCase;
		this.refreshTokenUseCase = refreshTokenUseCase;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,HttpServletRequest httpRequest) {
		var result = authenticateUseCase.authentication(new AuthenticationCommand(request.email(), request.password(),
				httpRequest.getHeader("User-Agent")));

		return ResponseEntity.ok(
				new LoginResponse(
					result.accessToken().token(), 
					result.accessToken().expireAt().toString(),
					result.refreshToken().token(),
					result.refreshToken().expireAt().toString(),
					result.staffId(), 
					result.fullName(), result.role(),
					result.mustChangePassword()
				));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refresh(
			@Valid @RequestBody RefreshTokenRequest request
			){
		var result =
				refreshTokenUseCase.refresh(
						new RefreshTokenCommand(request.refreshToken())
						);
		return ResponseEntity.ok(
				new LoginResponse(
						result.accessToken().token(),
                        result.accessToken().expireAt().toString(),

                        result.refreshToken().token(),
                        result.refreshToken().expireAt().toString(),

                        result.staffId(),
                        result.fullName(),
                        result.role(),
                        result.mustChangePassword()
						)
				);
				
	}
	
	

}
