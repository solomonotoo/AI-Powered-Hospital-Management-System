package com.ai_powered_hms_backend.shared_kernel.infrastructure.rest;

import java.time.OffsetDateTime;

public record ApiResponse<T>(
		 boolean success,
	        String message,
	        T data,
	        OffsetDateTime timestamp
		) {

	 public static <T> ApiResponse<T> success(
	            String message,
	            T data
	    ) {
	        return new ApiResponse<>(
	                true,
	                message,
	                data,
	                OffsetDateTime.now()
	        );
	    }
}
