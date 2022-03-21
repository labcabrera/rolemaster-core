package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Character status")
@RequestMapping("/character-status")
public interface CharacterStatusController {

	@PostMapping("/sesions/{sessionId}/characters/{characterId}")
	@Operation(summary = "Character status creation.")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
	Mono<CharacterStatus> createStatus(@PathVariable String sessionId, @PathVariable String characterId);

}
