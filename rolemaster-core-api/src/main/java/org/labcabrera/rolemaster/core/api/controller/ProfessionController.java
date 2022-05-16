package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.model.character.Profession;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/professions")
@Tag(name = "Professions", description = "List of existing professions.")
public interface ProfessionController {

	@GetMapping("/{id}")
	@Operation(summary = "Profession search by id.")
	Mono<Profession> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Profession search.")
	Flux<Profession> findAll(@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);
}
