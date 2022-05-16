package org.labcabrera.rolemaster.core.service.character.creation;

import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.service.character.item.CharacterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterCreationItemLoader {

	@Autowired
	private CharacterItemService itemService;

	public Mono<CharacterInfo> addItems(CharacterInfo character) {
		String id = character.getId();
		return itemService.addItem(id, AddCharacterItem.builder().itemId("boots").position(ItemPosition.CARRIED).build())
			.then(itemService.addItem(id, AddCharacterItem.builder().itemId("pants").position(ItemPosition.CARRIED).build()))
			.then(itemService.addItem(id, AddCharacterItem.builder().itemId("shirt").position(ItemPosition.CARRIED).build()))
			.then(itemService.addItem(id, AddCharacterItem.builder().itemId("coat").position(ItemPosition.CARRIED).build()))
			.then(itemService.addItem(id, AddCharacterItem.builder().itemId("backpack").position(ItemPosition.CARRIED).build()))
			.then(itemService.addItem(id, AddCharacterItem.builder().itemId("water-skin").position(ItemPosition.CARRIED).build()))
			.then(itemService.addItem(id, AddCharacterItem.builder().itemId("dagger").position(ItemPosition.MAIN_HAND).build()))
			.thenReturn(character);
	}

}
