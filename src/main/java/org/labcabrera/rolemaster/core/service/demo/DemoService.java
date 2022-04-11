package org.labcabrera.rolemaster.core.service.demo;

import java.io.InputStream;
import java.time.LocalDate;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.service.character.CharacterService;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.labcabrera.rolemaster.core.service.strategic.StrategicSessionService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DemoService {

	private static final String CREATION_REQUEST_RESOURCE = "openapi/examples/character-creation-example-01.json";

	@Autowired
	private CharacterService characterService;

	@Autowired
	private TacticalCharacterService characterStatusService;

	@Autowired
	private StrategicSessionService sessionService;

	@Autowired
	private CharacterCreationService characterCreationService;

	@Autowired
	private ObjectMapper objectMapper;

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

		StrategicSessionCreation sessionCreationRequest = StrategicSessionCreation.builder()
			.name("Demo session " + LocalDate.now().toString())
			.build();
		StrategicSession session = sessionService.createSession(sessionCreationRequest).share().block();
		String sessionId = session.getId();

		TacticalCharacter status = sessionService.addCharacter(sessionId, characterId).share().block();

		log.info("Created character status {}", status.getId());

		return Mono.justOrEmpty(null);
	}

	private CharacterCreationRequest createCharacterCreationRequest() {
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CREATION_REQUEST_RESOURCE)) {
			return objectMapper.readerFor(CharacterCreationRequest.class).readValue(in);
		}
		catch (Exception ex) {
			throw new RuntimeException("Error reading character creation request.", ex);
		}
	}

}
