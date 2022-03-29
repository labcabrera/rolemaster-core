package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.TacticalCharacterContextController;
import org.labcabrera.rolemaster.core.dto.CharacterTacticalContextModification;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.service.character.CharacterTacticalContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TacticalCharacterContextControllerImpl implements TacticalCharacterContextController {

	@Autowired
	private CharacterTacticalContextService characterStatusService;

	@Override
	public Mono<TacticalCharacterContext> findById(String id) {
		return characterStatusService.findById(id);
	}

	@Override
	public Flux<TacticalCharacterContext> findAll(Pageable pageable) {
		return characterStatusService.findAll(pageable);
	}

	@Override
	public Mono<TacticalCharacterContext> update(CharacterTacticalContextModification request) {
		// TODO Auto-generated method stub
		return null;
	}

}
