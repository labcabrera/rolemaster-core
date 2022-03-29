package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Character tactical context")
@RequestMapping("/character-tactical-context")
public interface CharacterTacticalContextController {

	@GetMapping("/{id}")
	@Operation(summary = "Character status search by id.")
	Mono<TacticalCharacterContext> findById(String id);

	@GetMapping
	@Operation(summary = "Character status search.")
	Flux<TacticalCharacterContext> findAll(
		@ParameterObject @PageableDefault(sort = "metadata.created", direction = Direction.DESC, size = 10) Pageable pageable);

	@PostMapping("/{id}/hp")
	@Operation(summary = "HP modification over an existing character status.")
	Mono<TacticalCharacterContext> hpModification(Integer value);

}
