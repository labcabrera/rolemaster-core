package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.dto.AddFlaw;
import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.AddTalent;
import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.dto.TrainingPackageUpgrade;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreation;
import org.labcabrera.rolemaster.core.model.ApiError;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/characters")
@Tag(name = "Characters", description = "Operations on characters such as search, creation, configuration or leveling up.")
public interface CharacterController {

	@GetMapping("/{id}")
	@Operation(summary = "Character search by id.")
	@ApiResponse(responseCode = "200", description = "Success")
	@ApiResponse(responseCode = "404", description = "Character not found", content = @Content(schema = @Schema(implementation = Void.class)))
	Mono<CharacterInfo> findById(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable String id);

	@GetMapping
	@Operation(summary = "Characters search.")
	Flux<CharacterInfo> findAll(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);

	@PostMapping
	@Operation(summary = "Character creation.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
	@ApiResponse(responseCode = "201", description = "Character created")
	@ApiResponse(responseCode = "400", description = "Validation error", content = @Content(schema = @Schema(implementation = ApiError.class)))
	Mono<CharacterInfo> create(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@Parameter(description = "Character creation request", required = true) @RequestBody(content = @Content(examples = {
			@ExampleObject(name = "Character creation example 01", ref = "#/components/examples/characterCreationExample01"),
			@ExampleObject(name = "Character creation example 02", ref = "#/components/examples/characterCreationExample01")
		})) @org.springframework.web.bind.annotation.RequestBody CharacterCreation request);

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Deleted character")
	@Operation(summary = "Delete character.")
	Mono<Void> deleteById(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable String id);

	@PostMapping("/{id}/skills")
	@Operation(summary = "Adds a skill to a character.")
	Mono<CharacterInfo> addSkill(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable("id") String characterId,
		@org.springframework.web.bind.annotation.RequestBody AddSkill request);

	@PostMapping("/{id}/skills/upgrade")
	@Operation(summary = "Performs a rank up operation of skill categories and skills.")
	Mono<CharacterInfo> updateRanks(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable("id") String characterId,
		@org.springframework.web.bind.annotation.RequestBody SkillUpgrade request);

	@PostMapping("/{id}/training-packages")
	@Operation(summary = "Applies a training package to a character.")
	Mono<CharacterInfo> addTrainingPackage(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable("id") String characterId,
		@org.springframework.web.bind.annotation.RequestBody TrainingPackageUpgrade request);

	@PostMapping("/{id}/talents")
	@Operation(summary = "Adds a talent to a character.")
	Mono<CharacterInfo> addTalent(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable("id") String characterId,
		@org.springframework.web.bind.annotation.RequestBody AddTalent request);

	@PostMapping("/{id}/flaws")
	@Operation(summary = "Adds a flaw to a character.")
	Mono<CharacterInfo> addFlaw(
		@Parameter(hidden = true) @AuthenticationPrincipal JwtAuthenticationToken auth,
		@PathVariable("id") String characterId,
		@org.springframework.web.bind.annotation.RequestBody AddFlaw request);

}
