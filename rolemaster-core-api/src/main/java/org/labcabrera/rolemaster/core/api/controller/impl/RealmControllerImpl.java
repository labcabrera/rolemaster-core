package org.labcabrera.rolemaster.core.api.controller.impl;

import java.util.Arrays;

import org.labcabrera.rolemaster.core.api.controller.RealmController;
import org.labcabrera.rolemaster.core.model.spell.Realm;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RealmControllerImpl implements RealmController {

	@Override
	public Mono<Realm> findById(String id) {
		return Mono.just(Realm.valueOf(id));
	}

	@Override
	public Flux<Realm> findAll(Pageable pageable) {
		return Flux.fromIterable(Arrays.asList(Realm.values()));
	}

}
