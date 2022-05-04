package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.StrategicSessionController;
import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.StrategicSessionUpdate;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.service.strategic.StrategicSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StrategicSessionControllerImpl implements StrategicSessionController {

	@Autowired
	private StrategicSessionService sessionService;

	@Autowired
	private StrategicSessionRepository repository;

	@Override
	public Mono<StrategicSession> findById(String id) {
		return sessionService.findById(id);
	}

	@Override
	public Flux<StrategicSession> findAll() {
		return sessionService.findAll();
	}

	@Override
	public Mono<StrategicSession> createSession(@Valid StrategicSessionCreation request) {
		return sessionService.createSession(request);
	}

	@Override
	public Mono<StrategicSession> updateSession(String id, @Valid StrategicSessionUpdate request) {
		return sessionService.updateSession(id, request);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Strategic session not found.")))
			.flatMap(repository::delete);
	}

}
