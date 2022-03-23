package org.labcabrera.rolemaster.core.controller.impl;

import java.util.Map;

import org.labcabrera.rolemaster.core.controller.CharacterCreationController;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class CharacterCreationControllerImpl implements CharacterCreationController {

	@Override
	public Mono<Integer> getAttributeCosts(Map<AttributeType, Integer> attributes) {
		// TODO
		return Mono.just(660);
	}

}
