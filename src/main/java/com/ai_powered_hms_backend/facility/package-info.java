@ApplicationModule(
    allowedDependencies = {
    		"shared_kernel::valueobjects", // Targets your named interface explicitly
    		"shared_kernel::enums",
    		"shared_kernel :: ids",
            "shared_kernel :: base",
            "shared_kernel :: persistence",
            "shared_kernel :: security",
            "shared_kernel :: exceptions",
            "shared_kernel :: rest"
    }
)
package com.ai_powered_hms_backend.facility;
import org.springframework.modulith.ApplicationModule;