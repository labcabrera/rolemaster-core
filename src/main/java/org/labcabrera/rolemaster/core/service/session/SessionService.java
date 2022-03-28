package org.labcabrera.rolemaster.core.service.session;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.SessionCreationRequest;
import org.labcabrera.rolemaster.core.dto.SessionUpdateRequest;
import org.labcabrera.rolemaster.core.exception.SessionNotFoundException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.session.Session;
import org.labcabrera.rolemaster.core.model.tactical.CharacterTacticalContext;
import org.labcabrera.rolemaster.core.repository.SessionRepository;
import org.labcabrera.rolemaster.core.service.character.CharacterTacticalContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class SessionService {

	@Autowired
	private SessionRepository repository;

	@Autowired
	private CharacterTacticalContextService characterStatusService;

	public Mono<Session> findById(String id) {
		return repository.findById(id);
	}

	public Flux<Session> findAll() {
		return repository.findAll();
	}

	public Mono<Session> createSession(SessionCreationRequest request) {
		Session session = Session.builder()
			.name(request.getName())
			.metadata(EntityMetadata.builder()
				.created(LocalDateTime.now())
				.build())
			.build();
		return repository.save(session);
	}

	public Mono<CharacterTacticalContext> addCharacter(String sessionId, String characterId) {
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

	public Mono<Session> updateSession(String id, SessionUpdateRequest request) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(new SessionNotFoundException(id)))
			.map(s -> {
				s.setName(request.getName());
				s.getMetadata().setUpdated(LocalDateTime.now());
				return s;
			})
			.flatMap(repository::save);
	}

	public Session addNpc(String sessionId, String npcId) {
		return null;
	}

}
