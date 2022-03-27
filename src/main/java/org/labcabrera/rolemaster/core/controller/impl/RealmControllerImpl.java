package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.RealmController;
import org.labcabrera.rolemaster.core.model.Realm;
import org.labcabrera.rolemaster.core.repository.RealmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RealmControllerImpl implements RealmController {

	@Autowired
	private RealmRepository repository;

	@Override
	public Mono<ResponseEntity<Realm>> findById(String id) {
		return repository.findById(id)
			.map(ResponseEntity::ok)
			.defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
	}

	@Override
	public Flux<Realm> findAll(Pageable pageable) {
		return repository.findAllByIdNotNullOrderByNameAsc(pageable);
	}

}
