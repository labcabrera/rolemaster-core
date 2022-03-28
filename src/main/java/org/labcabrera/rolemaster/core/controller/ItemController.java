package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.item.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Items")
@RequestMapping("/items")
public interface ItemController {

	@GetMapping
	Flux<Item> findAll();

	@GetMapping("/{id}")
	Mono<Item> findById(@PathVariable String id);

}
