package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.impl.CharacterCreationRequestImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Characters")
@RequestMapping("/characters")
public interface CharacterController {

	@GetMapping("/{id}")
	@Operation(summary = "Character search by id.")
	Mono<CharacterInfo> findById(@PathVariable String id);

	@PostMapping
	@ApiResponse(responseCode = "201", description = "Character created")
	@Operation(summary = "Character creation.")
	Mono<CharacterInfo> create(
		@Parameter(description = "Character creation request", required = true) @RequestBody(content = @Content(examples = {
			@ExampleObject(name = "Character creation example 01", ref = "#/components/examples/characterCreationExample01"),
			@ExampleObject(name = "Character creation example 02", ref = "#/components/examples/characterCreationExample01")
		})) @org.springframework.web.bind.annotation.RequestBody CharacterCreationRequestImpl request);
}
