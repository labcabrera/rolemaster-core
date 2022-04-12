package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionUpdate;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/tactical-sessions")
@Tag(name = "Tactical sessions", description = "Operations on the tactical environment.")
public interface TacticalSessionController {

	@GetMapping
	@Operation(summary = "Find all tactical sessions.")
	Flux<TacticalSession> find(
		@Parameter(description = "Strategic session identifier", name = "strategicSessionId", required = false, example = "624d70088f808c322db3cd27") @RequestParam(name = "strategicSessionId", required = false) String strategicSessionId,
		@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 20) Pageable pageable);

	@GetMapping("/{id}")
	@Operation(summary = "Find tactical session by id.")
	Mono<TacticalSession> findTacticalSessionsById(@PathVariable String id);

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Tactical session created.")
	@Operation(summary = "Create a new tactical session.")
	Mono<TacticalSession> createTacticalSession(@RequestBody TacticalSessionCreation request);

	@PatchMapping("/{id}")
	@Operation(summary = "Update tactical session by id.")
	Mono<TacticalSession> update(@PathVariable String id, @RequestBody TacticalSessionUpdate request);

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Tactical session deleted.")
	@Operation(summary = "Delete tactical session by id.")
	Mono<Void> deleteById(@PathVariable String id);

	@GetMapping("/{id}/round")
	@Operation(summary = "Get current round.")
	Mono<TacticalRound> findRound(@PathVariable("id") String tacticalSessionId);

	@PostMapping("/{id}/round")
	@Operation(summary = "Start round.")
	Mono<TacticalRound> startRound(@PathVariable("id") String tacticalSessionId);

	@GetMapping("/{id}/characters")
	@Operation(summary = "Find tactical session character contexts.")
	Flux<TacticalCharacter> findCharacters(@PathVariable("id") String id);

	@PostMapping("/{id}/characters/player/{characterId}")
	@Operation(summary = "Add a new character to current tactical session.")
	Mono<TacticalCharacter> addPlayerCharacter(@PathVariable("id") String id, @PathVariable("characterId") String characterId);

	@PostMapping("/{id}/characters/npc/{npcId}")
	@Operation(summary = "Add a new character to current tactical session.")
	Mono<TacticalCharacter> addNpcCharacter(@PathVariable("id") String id, @PathVariable("npcId") String npcId);

}
