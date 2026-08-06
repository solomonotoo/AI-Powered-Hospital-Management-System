package com.ai_powered_hms_backend.shared_kernel.infrastructure.security;

import java.util.UUID;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;




@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	 // TEMPORARY: fixed system-user UUID used when no X-User-Id header is supplied.
    // TODO: remove entirely once real authentication exists — every request
    // should then carry a genuine authenticated principal.
//	private static final UUID DEV_SYSTEM_USER_I_UUID = 
//			UUID.fromString("00000000-0000-0000-0000-000000000001");
//	
//	private static final String HEADER_NAME = "X-User-Id";
//	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// TODO Auto-generated method stub
		return parameter.hasParameterAnnotation(CurrentUserId.class)
				&& parameter.getParameterType().equals(UUID.class);
	}

//	@Override
//	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
//			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
//		
//		String header = webRequest.getHeader(HEADER_NAME);
//		
//	if(header == null || header.isBlank()) {
//		return DEV_SYSTEM_USER_I_UUID;
//	}
//		
//		try {
//			return UUID.fromString(header.trim());
//		} catch (IllegalArgumentException  e) {
//			throw new IllegalArgumentException(
//					"Invalid " + HEADER_NAME + " header - must be a valid UUID"
//					);
//		} 
//	}
	
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedPrincipal principal)) {
			throw new IllegalStateException("No authenticated user found for this request");
		}

		return principal.staffId().value();
	}
}
