package org.labcabrera.rolemaster.core.api.controller.impl;

import java.util.Map;

import org.labcabrera.rolemaster.core.api.controller.CharacterCreationController;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.service.character.creation.AttributeCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class CharacterCreationControllerImpl implements CharacterCreationController {

	@Autowired
	private AttributeCreationService attributeCreationService;

	@Override
	public Mono<Integer> getAttributeCosts(Map<AttributeType, Integer> attributes) {
		log.debug("Calculating attribute costs");
		return Mono.just(attributeCreationService.calculateCost(attributes));
	}

}
