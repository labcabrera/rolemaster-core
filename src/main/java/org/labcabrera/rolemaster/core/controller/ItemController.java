package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.item.ItemCategory;
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

@RequestMapping("/items")
@Tag(name = "Items", description = "List of equipment, weapons and armor options.")
public interface ItemController {

	@GetMapping
	@Operation(summary = "Seach all items.")
	Flux<ItemCategory> findAll(@ParameterObject @PageableDefault(sort = "name", direction = Direction.ASC, size = 10) Pageable pageable);

	@GetMapping("/{id}")
	@Operation(summary = "Gets a given item from its identifier.")
	Mono<ItemCategory> findById(@PathVariable String id);

}
