package org.labcabrera.rolemaster.core.api.controller.impl;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.api.controller.TacticalSessionController;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionUpdate;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TacticalControllerImpl implements TacticalSessionController {

	@Autowired
	private TacticalService tacticalService;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalCharacterRepository characterContextRepository;

	@Override
	public Mono<TacticalSession> createTacticalSession(JwtAuthenticationToken auth, TacticalSessionCreation request) {
		return tacticalService.createSession(auth, request);
	}

	@Override
	public Flux<TacticalSession> find(String strategicSessionId, Pageable pageable) {
		Example<TacticalSession> example = Example.of(TacticalSession.builder()
			.metadata(null)
			.build());
		if (StringUtils.isNotBlank(strategicSessionId)) {
			example.getProbe().setStrategicSessionId(strategicSessionId);
		}
		return tacticalSessionRepository.findAll(example, pageable.getSort());
	}

	@Override
	public Mono<TacticalSession> findTacticalSessionsById(String id) {
		return tacticalSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session not found.")));
	}

	@Override
	public Mono<TacticalSession> update(String id, TacticalSessionUpdate request) {
		return tacticalSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session not found.")))
			.map(e -> {
				e.setName(request.getName());
				e.setScale(request.getScale());
				e.setTerrain(request.getTerrain());
				e.setTemperature(request.getTemperature());
				e.setExhaustionMultiplier(request.getExhaustionMultiplier());
				e.setDescription(request.getDescription());
				e.getMetadata().setUpdated(LocalDateTime.now());
				return e;
			})
			.flatMap(tacticalSessionRepository::save);
	}

	@Override
	public Mono<Void> deleteById(String tacticalSessionId) {
		return tacticalService.deleteSession(tacticalSessionId);
	}

	@Override
	public Mono<TacticalCharacter> addPlayerCharacter(String id, String characterId) {
		return tacticalService.addCharacter(id, characterId);
	}

	@Override
	public Mono<TacticalCharacter> addNpcCharacter(String id, String npcId) {
		return tacticalService.addNpc(id, npcId);
	}

	@Override
	public Flux<TacticalCharacter> findCharacters(String tacticalSessionId) {
		return characterContextRepository.findByTacticalSessionId(tacticalSessionId);
	}

	@Override
	public Mono<TacticalRound> findRound(String tacticalSessionId) {
		return tacticalService.getCurrentRound(tacticalSessionId);
	}

	@Override
	public Mono<TacticalRound> startRound(String tacticalSessionId) {
		return tacticalService.startRound(tacticalSessionId);
	}

	@Override
	public Mono<TacticalRound> startInitiativeDeclaration(String tacticalSessionId) {
		return this.findRound(tacticalSessionId).flatMap(e -> tacticalService.startInitiativeDeclaration(e.getId()));
	}

	@Override
	public Mono<TacticalRound> declareInitiative(String tacticalSessionId, String characterId, Integer roll) {
		return this.findRound(tacticalSessionId).flatMap(e -> tacticalService.setInitiative(e.getId(), characterId, roll));
	}

	@Override
	public Mono<TacticalRound> startExecutionPhase(String tacticalSessionId) {
		return this.findRound(tacticalSessionId).flatMap(e -> tacticalService.startExecutionPhase(e.getId()));
	}

}
