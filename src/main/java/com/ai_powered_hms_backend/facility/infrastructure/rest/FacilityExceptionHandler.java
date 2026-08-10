package com.ai_powered_hms_backend.facility.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ai_powered_hms_backend.facility.application.service.FacilityCodeAlreadyExistsException;
import com.ai_powered_hms_backend.facility.application.service.FacilityNotFoundException;
import com.ai_powered_hms_backend.shared_kernel.exceptions.ErrorResponse;

@RestControllerAdvice(basePackages = "com.ai_powered_hms_backend.facility")
public class FacilityExceptionHandler {

	@ExceptionHandler(FacilityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(FacilityNotFoundException ex){
		return build(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	
	@ExceptionHandler(FacilityCodeAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateCode(FacilityCodeAlreadyExistsException ex){
		return build(HttpStatus.CONFLICT, ex.getMessage());
	}
	
	
	private ResponseEntity<ErrorResponse> build(HttpStatus status, String message){
		return ResponseEntity.status(status).body(new ErrorResponse(message, status.value()));
	}
}
