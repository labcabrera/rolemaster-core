package org.labcabrera.rolemaster.core.service.character.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class CharacterItemServiceTest {

	@Autowired
	private CharacterItemService characterItemService;

	@Autowired
	private CharacterCreationService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreation() throws IOException {
		CharacterCreationRequest request = readRequest();
		CharacterInfo characterInfo = service.create(request).share().block();

		AddCharacterItem addCoat = AddCharacterItem.builder()
			.itemId("reinforced-full-length-leather-coat")
			.position(ItemPosition.EQUIPED)
			.build();

		CharacterItem coat = characterItemService.addItem(characterInfo.getId(), addCoat).share().block();
		assertNotNull(coat);
		assertEquals("reinforced-full-length-leather-coat", coat.getItemId());
		assertEquals("Reinforced Full-Length Leather Coat", coat.getName());
		assertEquals(ItemType.ARMOR_PIECE, coat.getType());
		assertTrue(new BigDecimal("11").compareTo(coat.getWeigth()) == 0);

		AddCharacterItem addGreaves = AddCharacterItem.builder()
			.itemId("arm-greaves")
			.position(ItemPosition.EQUIPED)
			.build();

		CharacterItem armGreaves = characterItemService.addItem(characterInfo.getId(), addGreaves).share().block();
		assertNotNull(armGreaves);

		AddCharacterItem addBroadsword = AddCharacterItem.builder()
			.itemId("broadsword")
			.position(ItemPosition.MAIN_HAND)
			.build();

		CharacterItem broadsword = characterItemService.addItem(characterInfo.getId(), addBroadsword).share().block();
		assertNotNull(broadsword);

	}

	private CharacterCreationRequest readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreationRequest.class).readValue(in);
		}
	}
}
