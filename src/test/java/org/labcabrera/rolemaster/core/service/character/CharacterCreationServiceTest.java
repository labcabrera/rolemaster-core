package org.labcabrera.rolemaster.core.service.character;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
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
		CharacterCreationRequestImpl result = CharacterCreationRequestImpl.builder()
			.name("Set")
			.raceId("human")
			.attributesRoll(660)
			.build();
		result.getBaseAttributes().put(AttributeType.AGILITY, 96);
		result.getBaseAttributes().put(AttributeType.CONSTITUTION, 90);
		result.getBaseAttributes().put(AttributeType.MEMORY, 38);
		result.getBaseAttributes().put(AttributeType.REASONING, 43);
		result.getBaseAttributes().put(AttributeType.SELF_DISCIPLINE, 39);
		result.getBaseAttributes().put(AttributeType.EMPATHY, 20);
		result.getBaseAttributes().put(AttributeType.INTUTITION, 90);
		result.getBaseAttributes().put(AttributeType.PRESENCE, 50);
		result.getBaseAttributes().put(AttributeType.STRENGTH, 92);
		result.getBaseAttributes().put(AttributeType.QUICKNESS, 75);
		return result;
	}

}
