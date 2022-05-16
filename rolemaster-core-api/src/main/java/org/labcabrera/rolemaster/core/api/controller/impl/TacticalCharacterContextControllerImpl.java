package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.TacticalCharacterContextController;
import org.labcabrera.rolemaster.core.dto.CharacterTacticalContextModification;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TacticalCharacterContextControllerImpl implements TacticalCharacterContextController {

	@Autowired
	private TacticalCharacterService tacticalCharacterContextService;

	@Override
	public Mono<TacticalCharacter> findById(String id) {
		return tacticalCharacterContextService.findById(id);
	}

	@Override
	public Flux<TacticalCharacter> findAll(Pageable pageable) {
		return tacticalCharacterContextService.findAll(pageable);
	}

	@Override
	public Mono<TacticalCharacter> update(String id, CharacterTacticalContextModification request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> delete(String id) {
		return tacticalCharacterContextService.delete(id);
	}

}
