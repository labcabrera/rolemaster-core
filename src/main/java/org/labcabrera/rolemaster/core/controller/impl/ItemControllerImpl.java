package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.ItemController;
import org.labcabrera.rolemaster.core.model.item.Item;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ItemControllerImpl implements ItemController {

	@Autowired
	private ItemRepository repository;

	@Override
	public Flux<Item> find(ItemType type, Pageable pageable) {
		Item probe = new Item();
		probe.setCommonCost(null);
		probe.setCommonWeight(null);
		probe.setType(type);
		Example<Item> example = Example.of(probe);
		return repository.findAll(example, pageable.getSort());
	}

	@Override
	public Mono<Item> findById(String id) {
		return repository.findById(id);
	}

}
