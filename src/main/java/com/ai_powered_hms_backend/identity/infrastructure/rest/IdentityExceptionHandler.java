package com.ai_powered_hms_backend.identity.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ai_powered_hms_backend.identity.application.service.DuplicatePermissionException;
import com.ai_powered_hms_backend.identity.application.service.DuplicateRoleException;
import com.ai_powered_hms_backend.identity.application.service.InvalidCredentialsException;
import com.ai_powered_hms_backend.shared_kernel.exceptions.ErrorResponse;

@RestControllerAdvice(basePackages = "com.ai_powered_hms_backend.identity")
public class IdentityExceptionHandler {

	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
	}
	
	@ExceptionHandler(DuplicateRoleException.class)
	public ResponseEntity<ErrorResponse> handleDupulicateRole(DuplicateRoleException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()));
	}
	
	@ExceptionHandler(DuplicatePermissionException.class)
	public ResponseEntity<ErrorResponse> handleDupulicatePermission(DuplicatePermissionException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()));
	}
}
