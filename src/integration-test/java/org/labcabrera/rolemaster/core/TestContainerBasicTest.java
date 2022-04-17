package org.labcabrera.rolemaster.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import lombok.extern.slf4j.Slf4j;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
@Slf4j
public class TestContainerBasicTest {

	@Container
	private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.6");

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@DynamicPropertySource
	private static void dynamicProperties(DynamicPropertyRegistry registry) {
		mongoDBContainer.start();
		String mongoHost = mongoDBContainer.getHost();
		int mongoPort = mongoDBContainer.getMappedPort(27017);
		String mongoConnection = String.format("mongodb://%s:%s/rolemaster?retryWrites=true&w=majority", mongoHost, mongoPort);
		log.info("Using mongo container URI: {}", mongoConnection);
		registry.add("spring.data.mongodb.uri", () -> mongoConnection);
	}

	@Test
	public void testActuator() {
		String url = "http://localhost:" + port + "/actuator/health";
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
