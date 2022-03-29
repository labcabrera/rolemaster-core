package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.dto.CharacterTacticalContextModification;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/tactical/character/context")
@Tag(name = "Tactical character context", description = "Operations on the tactical environment of a character or NPC.")
public interface TacticalCharacterContextController {

	@GetMapping("/{id}")
	@Operation(summary = "Tactical character find by id.")
	Mono<TacticalCharacterContext> findById(String id);

	@GetMapping
	@Operation(summary = "Tactical character search.")
	Flux<TacticalCharacterContext> findAll(
		@ParameterObject @PageableDefault(sort = "metadata.created", direction = Direction.DESC, size = 10) Pageable pageable);

	@PostMapping("/{id}")
	@Operation(summary = "Tactical character update.")
	Mono<TacticalCharacterContext> update(@RequestBody CharacterTacticalContextModification request);

}
