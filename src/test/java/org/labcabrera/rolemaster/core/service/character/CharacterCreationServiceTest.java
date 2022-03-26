package org.labcabrera.rolemaster.core.service.character;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class CharacterCreationServiceTest {

	@Autowired
	private CharacterCreationService service;

	@Autowired
	private CharacterInfoRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreation() throws IOException {
		CharacterCreationRequest request = readRequest();

		Mono<CharacterInfo> setUp = repository
			.deleteAll()
			.then(service.create(request));

		StepVerifier
			.create(setUp)
			.expectNextCount(1)
			.verifyComplete();
	}

	private CharacterCreationRequest readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreationRequest.class).readValue(in);
		}
	}

}
