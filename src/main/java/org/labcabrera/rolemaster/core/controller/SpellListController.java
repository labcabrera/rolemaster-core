package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.spell.SpellList;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Spell lists")
@RequestMapping("/spell-lists")
public interface SpellListController {

	@GetMapping
	Flux<SpellList> findAll(@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);

	@GetMapping("/{id}")
	Mono<SpellList> findById(@PathVariable String id);

}
