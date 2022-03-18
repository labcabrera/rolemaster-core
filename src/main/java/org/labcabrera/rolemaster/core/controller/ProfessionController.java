package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.Profession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Professions")
@RequestMapping("/professions")
public interface ProfessionController {

	@GetMapping("/{id}")
	@Operation(summary = "Profession search by id.")
	Mono<Profession> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Profession search.")
	Flux<Profession> findAll();
}
