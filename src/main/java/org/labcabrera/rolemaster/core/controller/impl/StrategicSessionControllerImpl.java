package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.StrategicSessionController;
import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.StrategicSessionUpdate;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.service.strategic.StrategicSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StrategicSessionControllerImpl implements StrategicSessionController {

	@Autowired
	private StrategicSessionService sessionService;

	@Override
	public Mono<StrategicSession> findById(JwtAuthenticationToken auth, String id) {
		return sessionService.findById(auth, id);
	}

	@Override
	public Flux<StrategicSession> findAll(JwtAuthenticationToken auth, Pageable pageable) {
		return sessionService.findAll(auth, pageable);
	}

	@Override
	public Mono<StrategicSession> createSession(JwtAuthenticationToken auth, @Valid StrategicSessionCreation request) {
		return sessionService.createSession(auth, request);
	}

	@Override
	public Mono<StrategicSession> updateSession(JwtAuthenticationToken auth, String id, @Valid StrategicSessionUpdate request) {
		return sessionService.updateSession(id, request);
	}

	@Override
	public Mono<Void> deleteById(JwtAuthenticationToken auth, String id) {
		return sessionService.deleteById(auth, id);
	}

}
