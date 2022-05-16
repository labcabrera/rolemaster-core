package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.SpellListController;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.spell.SpellList;
import org.labcabrera.rolemaster.core.repository.SpellListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SpellListControllerImpl implements SpellListController {

	@Autowired
	private SpellListRepository repository;

	@Override
	public Mono<SpellList> findById(String id) {
		return repository.findById(id)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Spell list not found.")));
	}

	@Override
	public Flux<SpellList> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

}
