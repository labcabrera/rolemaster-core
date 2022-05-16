package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.UniverseController;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.Universe;
import org.labcabrera.rolemaster.core.repository.UniverseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UniverseControllerImpl implements UniverseController {

	@Autowired
	private UniverseRepository repository;

	@Override
	public Mono<Universe> findById(String id) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Universe not found.")));
	}

	@Override
	public Flux<Universe> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

}
