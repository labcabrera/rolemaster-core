package org.labcabrera.rolemaster.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTestBasic extends AbstractTestcontainers {

	private static final String ACTUATOR_URL = "/actuator/health";

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void checkActuator() {
		ResponseEntity<String> response = testRestTemplate.getForEntity(ACTUATOR_URL, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().contains("UP"));
	}
}
