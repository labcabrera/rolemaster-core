package org.labcabrera.rolemaster.core.controller.impl;

import org.labcabrera.rolemaster.core.controller.CharacterItemController;
import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.service.character.item.CharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CharacterItemControllerImpl implements CharacterItemController {

	@Autowired
	private CharacterItemService characterItemService;

	@Override
	public Mono<CharacterItem> getItem(String characterItemId) {
		return characterItemService.getItem(characterItemId);
	}

	@Override
	public Mono<Void> deleteItem(String characterItemId) {
		return characterItemService.deleteItem(characterItemId);
	}

	@Override
	public Mono<Void> equip(String characterItemId) {
		return characterItemService.equip(characterItemId);
	}

	@Override
	public Mono<Void> unequip(String characterItemId) {
		return characterItemService.unequip(characterItemId);
	}

	@Override
	public Mono<Void> store(String characterItemId) {
		return characterItemService.store(characterItemId);
	}

	@Override
	public Mono<CharacterItem> changeItemPosition(String characterItem, ItemPosition position) {
		return characterItemService.changeItemPosition(characterItem, position);
	}

	@Override
	public Flux<CharacterItem> getCharacterItems(String characterId, ItemType type, ItemPosition position) {
		return characterItemService.getCharacterItems(characterId, type, position);
	}

	@Override
	public Mono<CharacterItem> addItem(String characterId, AddCharacterItem request) {
		return characterItemService.addItem(characterId, request);
	}

}
