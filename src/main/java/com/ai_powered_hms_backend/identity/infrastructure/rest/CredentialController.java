package com.ai_powered_hms_backend.identity.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai_powered_hms_backend.identity.application.port.in.CreateUserCredentialCommand;
import com.ai_powered_hms_backend.identity.port.in.CreateUserCredentialUseCase;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.shared_kernel.valueobjects.Email;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/credentials")
public class CredentialController {

	private final CreateUserCredentialUseCase createUserCredentialUseCase;

	public CredentialController(CreateUserCredentialUseCase createUserCredentialUseCase) {
		this.createUserCredentialUseCase = createUserCredentialUseCase;
	}
	
	
	@PostMapping
	@PreAuthorize("hasAuthority('USER_MANAGE')") //Guard create credentials
	public ResponseEntity<Void> create(
			@Valid @RequestBody CreateCredentialRequest request
			){
		createUserCredentialUseCase.create(new CreateUserCredentialCommand(
				StaffId.of(request.staffId()), new Email(request.loginEmail()), request.temporaryPassword())
				);
		return ResponseEntity.ok().build();
	}
	
	
}
