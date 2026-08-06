package com.ai_powered_hms_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;


class AiPoweredHmsBackendApplicationTests {

	@Test
	void  verifiesModules() {
        ApplicationModules.of(AiPoweredHmsBackendApplication.class).verify();
    }

}
