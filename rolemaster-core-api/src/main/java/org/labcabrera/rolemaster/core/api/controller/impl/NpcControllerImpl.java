package org.labcabrera.rolemaster.core.api.controller.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.api.controller.NpcController;
import org.labcabrera.rolemaster.core.dto.AddNpc;
import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.labcabrera.rolemaster.core.repository.NpcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class NpcControllerImpl implements NpcController {

	@Autowired
	private NpcRepository repository;

	@Override
	public Flux<Npc> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

	@Override
	public Mono<Npc> findById(String id) {
		return repository.findById(id);
	}

	@Override
	public Mono<Npc> create(AddNpc request) {
		//TODO
		throw new NotImplementedException();
	}

}
