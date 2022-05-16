package org.labcabrera.rolemaster.core.service.character.item;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Mono;

@SpringBootTest
class CharacterItemServiceValidationTest {

	@Autowired
	private CharacterItemService characterItemService;

	@Test
	void testMissingCharacterId() throws IOException {
		AddCharacterItem addCoat = AddCharacterItem.builder()
			.itemId("reinforced-full-length-leather-coat")
			.position(ItemPosition.EQUIPED)
			.build();
		assertThrows(ConstraintViolationException.class, () -> characterItemService.addItem(null, addCoat));
	}

	@Test
	void testCharacterNotFound() throws IOException {
		AddCharacterItem addCoat = AddCharacterItem.builder()
			.itemId("reinforced-full-length-leather-coat")
			.position(ItemPosition.EQUIPED)
			.build();
		Mono<CharacterItem> share = characterItemService.addItem("invalid-id", addCoat).share();
		assertThrows(NotFoundException.class, () -> share.block());
	}

}
