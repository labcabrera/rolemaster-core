package org.labcabrera.rolemaster.core.api.controller.impl;

import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.api.controller.CharacterCreationController;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributeModifiers;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributes;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.exception.UnsupportedRolemasterVersionService;
import org.labcabrera.rolemaster.core.services.character.creation.AttributeCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class CharacterCreationControllerImpl implements CharacterCreationController {

	@Autowired
	private List<AttributeCreationService> attributeCreationServices;

	@Override
	public Mono<Integer> getAttributeCosts(Map<AttributeType, Integer> attributes) {
		throw new RuntimeException();
	}

	@Override
	public Mono<CharacterCreationAttributeModifiers> getAttributeModifiers(CharacterCreationAttributes attributes) {
		return route(attributes.getVersion()).getAttributeModifiers(attributes);
	}

	private AttributeCreationService route(RolemasterVersion version) {
		return attributeCreationServices.stream()
			.filter(e -> e.compatibleVersions().contains(version))
			.findFirst().orElseThrow(() -> new UnsupportedRolemasterVersionService(version));
	}

}
