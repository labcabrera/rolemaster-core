package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.dto.CharacterTacticalContextModification;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/tactical-character-contexts")
@Tag(name = "Tactical character contexts", description = "Operations on the tactical environment of a character or NPC.")
public interface TacticalCharacterContextController {

	@GetMapping("/{id}")
	@Operation(summary = "Tactical character find by id.")
	Mono<TacticalCharacter> findById(String id);

	@GetMapping
	@Operation(summary = "Tactical character search.")
	Flux<TacticalCharacter> findAll(
		@ParameterObject @PageableDefault(sort = "metadata.created", direction = Direction.DESC, size = 10) Pageable pageable);

	@PostMapping("/{id}")
	@Operation(summary = "Tactical character update.")
	Mono<TacticalCharacter> update(@PathVariable("id") String id, @RequestBody CharacterTacticalContextModification request);

	@DeleteMapping("/{id}")
	@Operation(summary = "Tactical character deletion.")
	@ApiResponse(responseCode = "204", description = "Tactical character context deleted.")
	@ApiResponse(responseCode = "404", description = "Not found.")
	Mono<Void> delete(@PathVariable("id") String id);

}
