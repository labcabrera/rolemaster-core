package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.model.spell.Spell;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/spells")
@Tag(name = "Spells", description = "List of spells.")
public interface SpellController {

	@GetMapping("/{id}")
	@Operation(summary = "It obtains a certain spell from its identifier.")
	Mono<Spell> findById(@PathVariable String id);

	@GetMapping
	@Operation(summary = "Spell search.")
	Flux<Spell> find(
		@Parameter(name = "spellListId", required = false) @RequestParam(required = false) String spellListId,
		@ParameterObject @PageableDefault(sort = "spellListId,level", direction = Direction.ASC, size = 10) Pageable pageable);

}
