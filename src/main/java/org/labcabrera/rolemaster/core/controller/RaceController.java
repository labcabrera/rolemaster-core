package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.character.Race;
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

@Tag(name = "Races")
@RequestMapping("/races")
public interface RaceController {

	@GetMapping("/{id}")
	@Operation(summary = "Race search by id.")
	Mono<Race> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Race search.")
	Flux<Race> findAll(@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);

}
