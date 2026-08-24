package com.ai_powered_hms_backend.identity.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai_powered_hms_backend.identity.application.command.RefreshTokenCommand;
import com.ai_powered_hms_backend.identity.application.port.in.RefreshTokenUseCase;
import com.ai_powered_hms_backend.identity.application.port.out.IssuedToken;
import com.ai_powered_hms_backend.identity.application.port.out.JwtTokenService;
import com.ai_powered_hms_backend.identity.application.port.out.SessionRepository;
import com.ai_powered_hms_backend.identity.application.port.out.TokenClaims;
import com.ai_powered_hms_backend.identity.application.port.out.UserCredentialRepository;
import com.ai_powered_hms_backend.identity.domain.model.UserCredential;
import com.ai_powered_hms_backend.shared_kernel.ids.StaffId;
import com.ai_powered_hms_backend.staff.application.api.StaffLookup;
import com.ai_powered_hms_backend.staff.application.api.StaffLookupSummary;

@Service
public class RefreshTokenService implements RefreshTokenUseCase {

	private final JwtTokenService jwtTokenService;
	private final UserCredentialRepository credentialRepository;
	private final StaffLookup staffLookup;
	private final SessionRepository sessionRepository;

	public RefreshTokenService(
	        JwtTokenService jwtTokenService,
	        UserCredentialRepository credentialRepository,
	        StaffLookup staffLookup,
	        SessionRepository sessionRepository) {

	    this.jwtTokenService = jwtTokenService;
	    this.credentialRepository = credentialRepository;
	    this.staffLookup = staffLookup;
	    this.sessionRepository = sessionRepository;
	}



	@Override
	@Transactional(readOnly = true)
	public RefreshResult refresh(RefreshTokenCommand command) {
		/*
         * 1. Validate refresh token.
         *
         * JwtTokenService is responsible for:
         * - signature validation
         * - expiration validation
         * - ensuring this is actually a refresh token
         */
		// Validate and parse refresh token
		TokenClaims claims = jwtTokenService.parseRefreshToken(command.refreshToken());
		
		StaffId staffId = claims.staffId();
		
		 
		 UserSession session = sessionRepository.findById(SessionId.of(claims.sessionId()))
				.orElseThrow(() -> new InvalidCredentialsException("Session not found"));

		if (!session.isValid()) {
			throw new InvalidCredentialsException("Session has been revoked or expired");
		}

		 /*
         * 2. Load the user's credentials.
         *
         * We do this because the account may have been
         * deactivated after the refresh token was issued.
         */
		UserCredential credential = 
				credentialRepository.findByStaffId(staffId)
				.orElseThrow(() ->
					new InvalidCredentialsException(   "Invalid refresh token")
						);
		
		/*
         * 3. Credential must still be active.
         */
		if(!credential.isActive()) {
			throw new InvalidCredentialsException("Account is deactivated");
		}
		

        /*
         * 4. Load current staff information.
         *
         * Do not rely on the role stored in the refresh token.
         * The staff's current role is authoritative.
         */
		StaffLookupSummary staff = staffLookup.getById(staffId.value());
		

        /*
         * 5. Staff record must still be active.
         */
		if(!staff.canAuthenticate()) {
			throw new InvalidCredentialsException("Staff record is inactive");
		}
		
		/*
         * 6. Issue a new access token.
         */
		IssuedToken accessToken = 
			jwtTokenService.issueAccessToken(staffId, staff.role(),claims.sessionId);
		
		/*
         * 7. Rotate the refresh token.
         */
        IssuedToken refreshToken =
                jwtTokenService.issueRefreshToken(
                        staffId,
                        staff.role(),
                        claims.sessionId
                );
		
/*
         * 9. Extend the session's tracked lifetime to match the new
         *    refresh token's expiry, and persist it.
         */
        session.renew(LocalDateTime.ofInstant(refreshToken.expireAt(), ZoneId.systemDefault()));
        sessionRepository.save(session);


        /*
         * 8. Return the new authentication information.
         */
		return new RefreshResult(
				accessToken,
				refreshToken,
				staffId.value().toString(),
				staff.fullName(),
				staff.role(),
				credential.mustChangePassword());// was hardcoded false — now reflects real state
	}

}
