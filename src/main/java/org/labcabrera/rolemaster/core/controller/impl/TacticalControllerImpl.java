package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.TacticalSessionController;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreationRequest;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TacticalControllerImpl implements TacticalSessionController {

	@Autowired
	private TacticalService tacticalService;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Override
	public Mono<TacticalSession> createTacticalSession(TacticalSessionCreationRequest request) {
		return tacticalService.createSession(request);
	}

	@Override
	public Flux<TacticalSession> findAllTacticalSessions() {
		return tacticalSessionRepository.findAll();
	}

	@Override
	public Mono<TacticalSession> findTacticalSessionsById(String id) {
		return tacticalSessionRepository.findById(id);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return tacticalSessionRepository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Tactical session not found.")))
			.flatMap(tacticalSessionRepository::delete);
	}

}
