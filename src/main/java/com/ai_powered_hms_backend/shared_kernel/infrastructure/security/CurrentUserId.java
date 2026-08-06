package com.ai_powered_hms_backend.shared_kernel.infrastructure.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TEMPORARY DEV-MODE MARKER.
 *
 * Resolves to the current authenticated user's UUID.
 * Currently backed by CurrentUserArgumentResolver, which reads an
 * X-User-Id header (or falls back to a fixed system user) since the
 * real Identity/login module does not exist yet.
 *
 * TODO: once JWT-based auth is built, swap the resolver's internals
 * to extract the UUID from the authenticated Spring Security principal.
 * No controller code should need to change — only this resolver.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CurrentUserId {
}
