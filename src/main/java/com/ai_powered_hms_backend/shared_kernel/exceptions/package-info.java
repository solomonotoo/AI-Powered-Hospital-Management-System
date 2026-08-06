/**
 * Shared, unchecked domain exception hierarchy. Every module throws these
 * (or a module-specific subclass of DomainException) rather than raw
 * RuntimeException, so shared.config.web.GlobalExceptionHandler can map
 * every business failure to a consistent HTTP response shape.
 */
@org.springframework.modulith.NamedInterface("exceptions")
package com.ai_powered_hms_backend.shared_kernel.exceptions;
