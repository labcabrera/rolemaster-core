package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.SessionController;
import org.labcabrera.rolemaster.core.dto.SessionCreationRequest;
import org.labcabrera.rolemaster.core.dto.SessionUpdateRequest;
import org.labcabrera.rolemaster.core.model.character.Session;
import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.labcabrera.rolemaster.core.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SessionControllerImpl implements SessionController {

	@Autowired
	private SessionService sessionService;

	@Override
	public Mono<Session> findById(String id) {
		return sessionService.findById(id);
	}

	@Override
	public Flux<Session> findAll() {
		return sessionService.findAll();
	}

	@Override
	public Mono<Session> createSession(@Valid SessionCreationRequest request) {
		return sessionService.createSession(request);
	}

	@Override
	public Mono<Session> updateSession(String id, @Valid SessionUpdateRequest request) {
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
	public Mono<CharacterStatus> createStatus(String sessionId, String characterId) {
		// TODO Auto-generated method stub
		return null;
	}

}
