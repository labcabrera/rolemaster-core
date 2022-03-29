package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.ItemController;
import org.labcabrera.rolemaster.core.model.item.Item;
import org.labcabrera.rolemaster.core.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ItemControllerImpl implements ItemController {

	@Autowired
	private ItemRepository repository;

	@Override
	public Flux<Item> findAll(Pageable pageable) {
		return repository.findAll(pageable.getSort());
	}

	@Override
	public Mono<Item> findById(String id) {
		return repository.findById(id);
	}

}