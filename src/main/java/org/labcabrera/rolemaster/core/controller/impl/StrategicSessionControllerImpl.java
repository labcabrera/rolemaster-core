package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.StrategicSessionController;
import org.labcabrera.rolemaster.core.dto.SessionCreationRequest;
import org.labcabrera.rolemaster.core.dto.SessionUpdateRequest;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.service.strategic.StrategicSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StrategicSessionControllerImpl implements StrategicSessionController {

	@Autowired
	private StrategicSessionService sessionService;

	@Override
	public Mono<StrategicSession> findById(String id) {
		return sessionService.findById(id);
	}

	@Override
	public Flux<StrategicSession> findAll() {
		return sessionService.findAll();
	}

	@Override
	public Mono<StrategicSession> createSession(@Valid SessionCreationRequest request) {
		return sessionService.createSession(request);
	}

	@Override
	public Mono<StrategicSession> updateSession(String id, @Valid SessionUpdateRequest request) {
		return sessionService.updateSession(id, request);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return sessionService.deleteAll(id);
	}

	@Override
	public Mono<Void> deleteAll() {
		return sessionService.deleteAll();
	}

	@Override
	public Mono<TacticalCharacterContext> createStatus(String sessionId, String characterId) {
		// TODO Auto-generated method stub
		return null;
	}

}
