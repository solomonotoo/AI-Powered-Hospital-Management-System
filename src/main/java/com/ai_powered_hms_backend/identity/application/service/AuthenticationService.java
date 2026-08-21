package com.ai_powered_hms_backend.identity.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.command.AuthenticationCommand;
import com.ai_powered_hms_backend.identity.application.port.in.AuthenticateUseCase;
import com.ai_powered_hms_backend.identity.application.port.out.IssuedToken;
import com.ai_powered_hms_backend.identity.application.port.out.JwtTokenService;
import com.ai_powered_hms_backend.identity.application.port.out.PasswordHasher;
import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository;
import com.ai_powered_hms_backend.identity.domain.model.UserCredential;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.staff.application.api.StaffLookup;
import com.ai_powered_hms_backend.staff.application.api.StaffLookupSummary;

@Service
public class AuthenticationService implements AuthenticateUseCase {
	
	private final UserCredentialRepository credentialRepository;
	private final PasswordHasher passwordHasher;
	private final JwtTokenService jwtTokenService;
	private final StaffLookup staffLookup;
	
	public AuthenticationService(UserCredentialRepository credentialRepository, PasswordHasher passwordHasher,
			JwtTokenService jwtTokenService, StaffLookup staffLookup) {
		super();
		this.credentialRepository = credentialRepository;
		this.passwordHasher = passwordHasher;
		this.jwtTokenService = jwtTokenService;
		this.staffLookup = staffLookup;
	}



	@Override
	@Transactional
	public AuthResult authentication(AuthenticationCommand command) {
		
		//get user credential by email
		UserCredential credential = credentialRepository.findByLoginEmail(command.email())
				.orElseThrow(() -> new InvalidCredentialsException("Invalide email or password"));
		
		//throw exception if user credential is not active
		if(!credential.isActive()) {
			throw new InvalidCredentialsException("Account is deactivated");
		}
		
		//throws exception if password mismatch
		if(!passwordHasher.matches(command.rawPassword(), credential.passwordHash())) {
			throw new InvalidCredentialsException("Invalid email or password");
		}
		
		//assigned the credentials to staff id
		StaffId staffId = credential.staffId();
		//get staff summary 
		StaffLookupSummary staff = staffLookup.getById(staffId.value());
	
		//throws exception if staff is not active
		if(!staff.canAuthenticate()) {
			throw new InvalidCredentialsException("Staff record is inactive");
		}
		
		//
		IssuedToken accessToken = jwtTokenService.issueAccessToken(staffId, staff.role());
		
		IssuedToken refreshToken = jwtTokenService.issueRefreshToken(staffId, staff.role());
		
		credential.recordSuccessfulLogin();
		credentialRepository.save(credential);
		
		return new AuthResult(accessToken,refreshToken, staffId.value().toString(), staff.fullName(), staff.role(), credential.mustChangePassword());
	}

}
