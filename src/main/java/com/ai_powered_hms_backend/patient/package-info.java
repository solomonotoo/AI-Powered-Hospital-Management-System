@ApplicationModule(
    allowedDependencies = {
    		"shared_kernel::valueobjects", // Targets your named interface explicitly
    		"shared_kernel::enums",
    		"shared_kernel::ids",
    		"shared_kernel::base",
    		"shared_kernel :: persistence",
    		"shared_kernel :: security",
    		"facility :: api",
    		"identity :: security"
    }
)
package com.ai_powered_hms_backend.patient;
import org.springframework.modulith.ApplicationModule;