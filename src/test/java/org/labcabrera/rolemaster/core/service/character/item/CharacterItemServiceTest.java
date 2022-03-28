package org.labcabrera.rolemaster.core.service.character.item;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterInventory;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.inventory.ItemStatus;
import org.labcabrera.rolemaster.core.repository.CharacterInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CharacterItemServiceTest {

	@Autowired
	private CharacterItemService characterItemService;

	@Autowired
	private CharacterInventoryRepository characterInventoryRepository;

	@Test
	void test() {
		characterInventoryRepository.deleteByCharacterId("c-01").share().block();

		CharacterInventory ci = CharacterInventory.builder()
			.characterId("c-01")
			.build();

		ci = characterInventoryRepository.save(ci).share().block();

		CharacterItem characterItem = CharacterItem.builder()
			.itemId("type-01")
			.weigth(new BigDecimal("0.555"))
			.status(ItemStatus.CARRIED)
			.build();

		ci = characterItemService.addItem("c-01", characterItem).share().block();

		assertEquals(1, ci.getItems().size());
		assertEquals(new BigDecimal("0.555"), characterItemService.getWeight(ci));

		characterInventoryRepository.deleteById(ci.getId()).share().block();

	}

}
