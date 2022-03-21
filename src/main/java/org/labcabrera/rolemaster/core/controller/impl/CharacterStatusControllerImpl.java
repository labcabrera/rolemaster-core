package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.CharacterStatusController;
import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.labcabrera.rolemaster.core.service.character.CharacterStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CharacterStatusControllerImpl implements CharacterStatusController {

	@Autowired
	private CharacterStatusService characterStatusService;

	@Override
	public Mono<CharacterStatus> findById(String id) {
		return characterStatusService.findById(id);
	}

	@Override
	public Flux<CharacterStatus> findAll() {
		return characterStatusService.findAll();
	}

}
