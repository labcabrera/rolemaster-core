package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterModification;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/tactical-characters")
@Tag(name = "Tactical characters", description = "Operations on the tactical environment of a character or NPC.")
public interface TacticalCharacterController {

	@GetMapping("/{id}")
	@Operation(summary = "Tactical character find by id.")
	Mono<TacticalCharacter> findById(String id);

	@GetMapping
	@Operation(summary = "Tactical character search.")
	Flux<TacticalCharacter> findAll(
		@ParameterObject @PageableDefault(sort = "metadata.created", direction = Direction.DESC, size = 10) Pageable pageable);

	@DeleteMapping("/{id}")
	@Operation(summary = "Tactical character deletion.")
	@ApiResponse(responseCode = "204", description = "Tactical character context deleted.")
	@ApiResponse(responseCode = "404", description = "Not found.")
	Mono<Void> delete(@PathVariable("id") String id);

	@PatchMapping("/{id}")
	Mono<TacticalCharacter> update(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable("id") String tacticalCharacterId,
		@RequestBody TacticalCharacterModification modification);

}
