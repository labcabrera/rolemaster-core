package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.RealmController;
import org.labcabrera.rolemaster.core.model.Realm;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RealmControllerImpl implements RealmController {

	@Override
	public Mono<Realm> findById(String id) {
		return null;
	}

	@Override
	public Flux<Realm> findAll() {
		return null;
	}

}
