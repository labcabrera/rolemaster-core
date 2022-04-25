package org.labcabrera.rolemaster.core.service.strategic;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.StrategicSessionUpdate;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.exception.SessionNotFoundException;
import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StrategicSessionService {

	@Autowired
	private StrategicSessionRepository strategicSessionRepository;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalService tacticalService;

	@Autowired
	private TacticalCharacterService characterStatusService;

	public Mono<StrategicSession> findById(String id) {
		return strategicSessionRepository.findById(id);
	}

	public Flux<StrategicSession> findAll() {
		return strategicSessionRepository.findAll();
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
		return strategicSessionRepository.save(session);
	}

	public Mono<TacticalCharacter> addCharacter(String sessionId, String characterId) {
		return characterStatusService.create(sessionId, characterId);
	}

	public Mono<Void> deleteSession(String id) {
		return strategicSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Strategic session not found.")))
			.thenMany(tacticalSessionRepository.findAll())
			.flatMap(session -> tacticalService.deleteSession(session.getId()))
			.then(strategicSessionRepository.deleteById(id));
	}

	public Mono<StrategicSession> updateSession(String id, StrategicSessionUpdate request) {
		return strategicSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(new SessionNotFoundException(id)))
			.map(s -> {
				s.setName(request.getName() != null ? request.getName() : s.getName());
				s.setDescription(request.getDescription() != null ? request.getDescription() : s.getDescription());
				s.getMetadata().setUpdated(LocalDateTime.now());
				return s;
			})
			.flatMap(strategicSessionRepository::save);
	}

}
