package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.Session;
import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Sessions")
@RequestMapping("/sessions")
public interface SessionController {

	@GetMapping("/{id}")
	@Operation(summary = "Get session.")
	Mono<Session> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Get all sessions.")
	Flux<Session> findAll();

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
	@Operation(summary = "Create new session.")
	Mono<Session> createSession(String name);

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
	Mono<CharacterStatus> createStatus(@PathVariable("id") String sessionId, @PathVariable("characterId") String characterId);

}
