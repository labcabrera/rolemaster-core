package org.labcabrera.rolemaster.core.api.controller;

import java.util.Map;

import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributeModifiers;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributes;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Character creation", description = "Common utilities for character creation.")
@RequestMapping("/character-creation")
public interface CharacterCreationController {

	@Deprecated
	@PostMapping("/attribute-costs")
	@Operation(summary = "Calculate attribute costs.")
	Mono<Integer> getAttributeCosts(@RequestBody Map<AttributeType, Integer> attributes);

	@PostMapping("/attributes")
	@Operation(summary = "Get character creation attribute modifiers.")
	Mono<CharacterCreationAttributeModifiers> getAttributeModifiers(@RequestBody CharacterCreationAttributes attributes);

}
