package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.RaceController;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RaceControllerImpl implements RaceController {

	@Autowired
	private RaceRepository repository;

	public Mono<Race> findById(@PathVariable String id) {
		return repository.findById(id);
	}

	public Flux<Race> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}
}
