package org.labcabrera.rolemaster.core.service.strategic;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.StrategicSessionUpdate;
import org.labcabrera.rolemaster.core.exception.SessionNotFoundException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class StrategicSessionService {

	@Autowired
	private StrategicSessionRepository repository;

	@Autowired
	private TacticalCharacterContextService characterStatusService;

	public Mono<StrategicSession> findById(String id) {
		return repository.findById(id);
	}

	public Flux<StrategicSession> findAll() {
		return repository.findAll();
	}

	public Mono<StrategicSession> createSession(StrategicSessionCreation request) {
		StrategicSession session = StrategicSession.builder()
			.name(request.getName())
			.description(request.getDescription())
			.universeId(request.getUniverseId())
			.metadata(EntityMetadata.builder()
				.created(LocalDateTime.now())
				.build())
			.build();
		return repository.save(session);
	}

	public Mono<TacticalCharacterContext> addCharacter(String sessionId, String characterId) {
		return characterStatusService.create(sessionId, characterId);
	}

	public Mono<Void> deleteAll() {
		log.info("Deleting all sessions");
		return repository.deleteAll();
	}

	public Mono<Void> deleteAll(String id) {
		log.info("Deleting session {}", id);
		return repository.deleteById(id).thenEmpty(e -> log.info("Deleted session {}", id));
	}

	public Mono<StrategicSession> updateSession(String id, StrategicSessionUpdate request) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(new SessionNotFoundException(id)))
			.map(s -> {
				s.setName(request.getName() != null ? request.getName() : s.getName());
				s.setDescription(request.getDescription() != null ? request.getDescription() : s.getDescription());
				s.getMetadata().setUpdated(LocalDateTime.now());
				return s;
			})
			.flatMap(repository::save);
	}

	public StrategicSession addNpc(String sessionId, String npcId) {
		return null;
	}

}
