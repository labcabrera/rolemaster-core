package org.labcabrera.rolemaster.core.controller;

import java.util.Map;

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

	@PostMapping("/attribute-costs")
	@Operation(summary = "Calculate attribute costs.")
	Mono<Integer> getAttributeCosts(@RequestBody Map<AttributeType, Integer> attributes);

}
