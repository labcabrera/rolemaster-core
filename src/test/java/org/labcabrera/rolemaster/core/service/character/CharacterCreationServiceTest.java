package org.labcabrera.rolemaster.core.service.character;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.character.creation.impl.CharacterCreationRequestImpl;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class CharacterCreationServiceTest {

	@Autowired
	private CharacterCreationService service;

	@Autowired
	private CharacterInfoRepository repository;

	@Test
	void testCreation() {
		CharacterCreationRequest request = createRequest();
		repository.deleteAll().thenMany(service.create(request));
		Mono<CharacterInfo> setUp = repository.deleteAll().then(service.create(request));
		StepVerifier
			.create(setUp)
			.expectNextCount(1)
			.verifyComplete();
	}

	private CharacterCreationRequest createRequest() {
		return CharacterCreationRequestImpl.builder()
			.name("Set")
			.raceId("human")
			.build();
	}

}
