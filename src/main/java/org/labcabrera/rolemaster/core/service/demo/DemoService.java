package org.labcabrera.rolemaster.core.service.demo;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.Session;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequestImpl;
import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.labcabrera.rolemaster.core.service.character.CharacterService;
import org.labcabrera.rolemaster.core.service.character.CharacterStatusService;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.labcabrera.rolemaster.core.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DemoService {

	@Autowired
	private CharacterService characterService;

	@Autowired
	private CharacterStatusService characterStatusService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private CharacterCreationService characterCreationService;

	public Mono<Void> cleanUp() {
		log.info("Cleaning up data");
		characterStatusService.deleteAll()
			.then(characterService.deleteAll())
			.then(sessionService.deleteAll())
			.subscribe();
		return Mono.justOrEmpty(null);
	}

	public Mono<Void> initialize() {
		CharacterCreationRequest request = createCharacterCreationRequest();
		characterStatusService.deleteAll().share().block();
		sessionService.deleteAll().share().block();
		characterService.deleteAll().share().block();

		Mono<CharacterInfo> monoCreate = characterCreationService.create(request);
		CharacterInfo character = monoCreate.share().block();
		String characterId = character.getId();

		Session session = sessionService.createSession("Dummy session").share().block();
		String sessionId = session.getId();

		CharacterStatus status = sessionService.addCharacter(sessionId, characterId).share().block();

		log.info("Created character status {}", status.getId());

		return Mono.justOrEmpty(null);
	}

	private CharacterCreationRequest createCharacterCreationRequest() {
		CharacterCreationRequestImpl result = CharacterCreationRequestImpl.builder()
			.name("Set (demo)")
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
