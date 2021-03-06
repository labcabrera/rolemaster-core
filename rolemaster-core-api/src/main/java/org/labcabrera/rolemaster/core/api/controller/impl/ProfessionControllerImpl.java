package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.ProfessionController;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProfessionControllerImpl implements ProfessionController {

	@Autowired
	private ProfessionRepository repository;

	@Override
	public Mono<Profession> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Flux<Profession> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

}
