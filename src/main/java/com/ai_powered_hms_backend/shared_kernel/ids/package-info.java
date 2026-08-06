//@NamedInterface("ids")
//package com.hms_application.shared.kernel.ids;
//
//import org.springframework.modulith.NamedInterface;

/**
 * Typed identifiers for every aggregate, shared across all modules.
 * Pure records wrapping UUID — no JPA imports here. JPA AttributeConverters
 * for each ID live in shared.config.persistence.converters, since converting
 * to/from a database column is infrastructure concern, not domain vocabulary.
 */
@org.springframework.modulith.NamedInterface("ids")
package com.ai_powered_hms_backend.shared_kernel.ids;