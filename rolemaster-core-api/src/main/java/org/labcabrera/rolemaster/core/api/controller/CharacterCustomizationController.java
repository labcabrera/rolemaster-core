package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.CharacterCustomization;
import org.labcabrera.rolemaster.core.model.character.CharacterCustomizationType;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/character-customizations")
@Tag(name = "Items", description = "List of equipment, weapons and armor options.")
public interface CharacterCustomizationController {

	@GetMapping("/{id}")
	@Operation(summary = "Character customization by id.")
	Mono<CharacterCustomization> getItem(@PathVariable("id") String customizationId);

	@GetMapping
	@Operation(summary = "Character customization search.")
	Flux<CharacterCustomization> getItem(
		@Parameter(name = "version", required = false) @RequestParam(required = false) RolemasterVersion version,
		@Parameter(name = "type", required = false) @RequestParam(required = false) CharacterCustomizationType type,
		@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 100) Pageable pageable);
}
