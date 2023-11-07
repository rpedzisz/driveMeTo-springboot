package com.makolab.cc.bootcampRP;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:tests.properties")
class ApplicationTest {

	@Test
	void contextLoads() {
	}

}
