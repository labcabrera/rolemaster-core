package org.labcabrera.rolemaster.core;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

//@Testcontainers
public abstract class AbstractTestcontainers {

	private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.6");

	@DynamicPropertySource
	private static void dynamicProperties(DynamicPropertyRegistry registry) {
		mongoDBContainer.start();

		String mongoHost = mongoDBContainer.getHost();
		int mongoPort = mongoDBContainer.getMappedPort(27017);
		String mongoConnection = String.format("mongodb://%s:%s/rolemaster?retryWrites=true&w=majority", mongoHost, mongoPort);
		registry.add("spring.data.mongodb.uri", () -> mongoConnection);
	}
}