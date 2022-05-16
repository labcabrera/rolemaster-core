package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.model.spell.Realm;
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

@RequestMapping("/realms")
@Tag(name = "Realms", description = "List of realms of power.")
public interface RealmController {

	@GetMapping("/{id}")
	@Operation(summary = "Realm of power search by id.")
	Mono<Realm> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Realm of power search.")
	Flux<Realm> findAll(
		@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);

}
