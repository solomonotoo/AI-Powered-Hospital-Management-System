package com.ai_powered_hms_backend.shared_kernel.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//generic, module-agnostic handling only
//None of these exception types (IllegalArgumentException, IllegalStateException, MethodArgumentNotValidException)
//belong to any business module — they're JDK/Spring types, so referencing them from shared_kernel violates nothing.


@RestControllerAdvice
public class GlobalExceptionHandler {


	 private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex){
		 String message = ex.getBindingResult().getFieldErrors().stream()
				 .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
				 .reduce((a,b) -> a + "; " + b)
				 .orElse("Validation failed");
		 
		 return build(HttpStatus.BAD_REQUEST, message);
	 }
	 
	 
	 @ExceptionHandler(IllegalArgumentException.class)
	 public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex){
		 return build(HttpStatus.BAD_REQUEST, ex.getMessage());
	 }
	 
	 @ExceptionHandler(IllegalStateException.class)
	    public ResponseEntity<ErrorResponse> handleIllegalState(IllegalStateException ex) {
	        return build(HttpStatus.CONFLICT, ex.getMessage());
	    }
	 
	 @ExceptionHandler(Exception.class)
	 public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex){
		 LOGGER.error("Unhandled exception", ex);
		 return build(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occured");
	 }
	 
	 
	 private ResponseEntity<ErrorResponse> build(
		        HttpStatus status,
		        String message) {

		    return ResponseEntity
		            .status(status)
		            .body(new ErrorResponse(message, status.value()));
		}
}
