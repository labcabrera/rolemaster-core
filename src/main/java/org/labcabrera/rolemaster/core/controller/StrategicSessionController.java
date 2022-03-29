package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.dto.SessionCreationRequest;
import org.labcabrera.rolemaster.core.dto.SessionUpdateRequest;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Strategic sessions")
@RequestMapping("/strategic-sessions")
public interface StrategicSessionController {

	@GetMapping("/{id}")
	@Operation(summary = "Get session.")
	@ApiResponse(responseCode = "200", description = "Success")
	@ApiResponse(responseCode = "404", description = "Not found")
	Mono<StrategicSession> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Get all sessions.")
	@ResponseStatus(code = HttpStatus.OK, reason = "Success")
	Flux<StrategicSession> findAll();

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Session created.")
	@Operation(summary = "Session creation.")
	Mono<StrategicSession> createSession(@RequestBody SessionCreationRequest sessionCreationRequest);

	@PatchMapping("/{id}")
	@Operation(summary = "Session update.")
	@ApiResponse(responseCode = "200", description = "Updated")
	@ApiResponse(responseCode = "404", description = "Not found")
	Mono<StrategicSession> updateSession(@PathVariable String id, @RequestBody SessionUpdateRequest sessionUpdateRequest);

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Deleted session")
	@Operation(summary = "Delete session.")
	Mono<Void> deleteById(@PathVariable String id);

	@DeleteMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Deleted sessions")
	@Operation(summary = "Delete all sessions.")
	Mono<Void> deleteAll();

	@PostMapping("/{id}/characters/{characterId}")
	@Operation(summary = "Adds a certain character to the session.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
	Mono<TacticalCharacterContext> createStatus(@PathVariable("id") String sessionId, @PathVariable("characterId") String characterId);

}
