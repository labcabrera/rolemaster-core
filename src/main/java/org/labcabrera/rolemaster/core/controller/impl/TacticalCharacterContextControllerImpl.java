package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.TacticalCharacterContextController;
import org.labcabrera.rolemaster.core.dto.CharacterTacticalContextModification;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TacticalCharacterContextControllerImpl implements TacticalCharacterContextController {

	@Autowired
	private TacticalCharacterContextService tacticalCharacterContextService;

	@Override
	public Mono<TacticalCharacterContext> findById(String id) {
		return tacticalCharacterContextService.findById(id);
	}

	@Override
	public Flux<TacticalCharacterContext> findAll(Pageable pageable) {
		return tacticalCharacterContextService.findAll(pageable);
	}

	@Override
	public Mono<TacticalCharacterContext> update(String id, CharacterTacticalContextModification request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> delete(String id) {
		return tacticalCharacterContextService.delete(id);
	}

}
