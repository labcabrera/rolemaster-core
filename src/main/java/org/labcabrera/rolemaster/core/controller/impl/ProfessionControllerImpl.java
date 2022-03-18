package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.ProfessionController;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProfessionControllerImpl implements ProfessionController {

	@Override
	public Mono<Profession> findById(String id) {
		return null;
	}

	@Override
	public Flux<Profession> findAll() {
		return null;
	}

}
