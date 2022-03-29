package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.SpellController;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.spell.Spell;
import org.labcabrera.rolemaster.core.repository.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SpellControllerImpl implements SpellController {

	@Autowired
	private SpellRepository repository;

	@Override
	public Mono<Spell> findById(String id) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Spell not found.")));
	}

	@Override
	public Flux<Spell> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

}