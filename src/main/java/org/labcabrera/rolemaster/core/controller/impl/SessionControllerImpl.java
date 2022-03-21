package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.SessionController;
import org.labcabrera.rolemaster.core.model.character.Session;
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
	public Mono<Session> createSession(String name) {
		return sessionService.createSession(name);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return sessionService.deleteAll(id);
	}

	@Override
	public Mono<Void> deleteAll() {
		return sessionService.deleteAll();
	}

}
