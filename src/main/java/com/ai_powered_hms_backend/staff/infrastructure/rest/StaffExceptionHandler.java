package com.ai_powered_hms_backend.staff.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ai_powered_hms_backend.shared_kernel.exceptions.ErrorResponse;
import com.ai_powered_hms_backend.staff.application.service.DuplicateStaffException;

@RestControllerAdvice(basePackages = "com.ai_powered_hms_backend.staff")
public class StaffExceptionHandler {

	@ExceptionHandler(DuplicateStaffException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateStaff(DuplicateStaffException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()));
	}
	
}

