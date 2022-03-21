package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Character status")
@RequestMapping("/character-status")
public interface CharacterStatusController {

	@GetMapping("/{id}")
	@Operation(summary = "Character status search by id.")
	Mono<CharacterStatus> findById(String id);

	@GetMapping
	@Operation(summary = "Character status search.")
	Flux<CharacterStatus> findAll();

}
