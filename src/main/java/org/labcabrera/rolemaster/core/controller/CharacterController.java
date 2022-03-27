package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.dto.SkillUpgradeRequest;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Characters")
@RequestMapping("/characters")
public interface CharacterController {

	@GetMapping("/{id}")
	@Operation(summary = "Character search by id.")
	Mono<CharacterInfo> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Characters search.")
	Flux<CharacterInfo> findAll();

	@PostMapping
	@ApiResponse(responseCode = "201", description = "Character created")
	@Operation(summary = "Character creation.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
	Mono<CharacterInfo> create(
		@Parameter(description = "Character creation request", required = true) @RequestBody(content = @Content(examples = {
			@ExampleObject(name = "Character creation example 01", ref = "#/components/examples/characterCreationExample01"),
			@ExampleObject(name = "Character creation example 02", ref = "#/components/examples/characterCreationExample01")
		})) @org.springframework.web.bind.annotation.RequestBody CharacterCreationRequest request);

	@PostMapping("/{id}/skills/upgrade")
	Mono<CharacterInfo> updateRanks(@PathVariable String characterId, SkillUpgradeRequest request);

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Deleted character")
	@Operation(summary = "Delete character.")
	Mono<Void> deleteById(@PathVariable String id);

	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Deleted characters")
	@Operation(summary = "Delete all characters.")
	Mono<Void> deleteAll();

}
