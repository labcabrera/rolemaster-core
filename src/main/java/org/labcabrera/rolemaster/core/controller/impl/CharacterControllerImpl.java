package org.labcabrera.rolemaster.core.controller.impl;

import javax.validation.Valid;

import org.labcabrera.rolemaster.core.controller.CharacterController;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.impl.CharacterCreationRequestImpl;
import org.labcabrera.rolemaster.core.service.character.CharacterCreationService;
import org.labcabrera.rolemaster.core.service.character.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CharacterControllerImpl implements CharacterController {

	@Autowired
	private CharacterService characterService;

	@Autowired
	private CharacterCreationService creationService;

	@Override
	public Mono<CharacterInfo> findById(String id) {
		return characterService.findById(id);
	}

	@Override
	public Mono<CharacterInfo> create(@Valid CharacterCreationRequestImpl request) {
		return creationService.create(request);
	}

	@Override
	public Flux<CharacterInfo> findAll() {
		return characterService.findAll();
	}

	@Override
	public Mono<Void> deleteById(String id) {
		return characterService.deleteById(id);
	}

	@Override
	public Mono<Void> deleteAll() {
		return characterService.deleteAll();
	}

}
