package org.labcabrera.rolemaster.core.controller.impl;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.controller.TacticalSessionController;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionUpdate;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
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
	private TacticalCharacterContextRepository characterContextRepository;

	@Override
	public Mono<TacticalSession> createTacticalSession(TacticalSessionCreation request) {
		return tacticalService.createSession(request);
	}

	@Override
	public Flux<TacticalSession> find(String strategicSessionId, Pageable pageable) {
		Example<TacticalSession> example = Example.of(new TacticalSession());
		example.getProbe().setCurrentRound(null);
		if (StringUtils.isNotBlank(strategicSessionId)) {
			example.getProbe().setStrategicSessionId(strategicSessionId);
		}
		return tacticalSessionRepository.findAll(example, pageable.getSort());
	}

	@Override
	public Mono<TacticalSession> findTacticalSessionsById(String id) {
		return tacticalSessionRepository.findById(id);
	}

	@Override
	public Mono<TacticalSession> update(String id, TacticalSessionUpdate request) {
		return tacticalSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session not found.")))
			.map(e -> {
				e.setName(request.getName());
				e.setDescription(request.getDescription());
				e.getEntityMetadata().setUpdated(LocalDateTime.now());
				return e;
			})
			.flatMap(tacticalSessionRepository::save);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return tacticalSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session not found.")))
			.flatMap(tacticalSessionRepository::delete);
	}

	@Override
	public Mono<TacticalCharacterContext> addPlayerCharacter(String id, String characterId) {
		return tacticalService.addCharacter(id, characterId);
	}

	@Override
	public Mono<TacticalCharacterContext> addNpcCharacter(String id, String npcId) {
		return tacticalService.addNpc(id, npcId);
	}

	@Override
	public Flux<TacticalCharacterContext> findCharacters(String tacticalSessionId) {
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

}
