package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.npc.Npc;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/npc")
@Tag(name = "Non playable charactes", description = "List of non-player characters.")
public interface NpcController {

	@GetMapping("/{id}")
	@Operation(summary = "NPC search by id.")
	Mono<Npc> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "NPC search.")
	Flux<Npc> findAll(@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);

	@PostMapping
	@Operation(summary = "NPC creation.")
	@PreAuthorize("hasAuthority('SCOPE_NPC:CREATION')")
	Mono<Npc> create(@RequestBody Npc npc);

}
