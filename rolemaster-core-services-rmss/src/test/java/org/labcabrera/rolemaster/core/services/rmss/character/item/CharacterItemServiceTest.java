package org.labcabrera.rolemaster.core.services.rmss.character.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreation;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.services.character.CharacterAddSkillService;
import org.labcabrera.rolemaster.core.services.character.creation.CharacterCreationService;
import org.labcabrera.rolemaster.core.services.character.item.CharacterItemService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class CharacterItemServiceTest {

	@Autowired
	private CharacterItemService characterItemService;

	@Autowired
	private CharacterCreationService creationService;

	@Autowired
	private CharacterAddSkillService addSkillService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreation() throws IOException {
		JwtAuthenticationToken auth = Mockito.mock(JwtAuthenticationToken.class);
		Mockito.when(auth.getName()).thenReturn("test");

		CharacterCreation request = readRequest();
		CharacterInfo characterInfo = creationService.create(auth, request).share().block();

		characterInfo = addSkillService.addSkill(auth, characterInfo.getId(), AddSkill.builder().skillId("soft-leather").build()).share()
			.block();

		AddCharacterItem addCoat = AddCharacterItem.builder()
			.itemId("reinforced-full-length-leather-coat")
			.position(ItemPosition.EQUIPED)
			.build();

		CharacterItem coat = characterItemService.addItem(characterInfo.getId(), addCoat).share().block();
		assertNotNull(coat);
		assertEquals("reinforced-full-length-leather-coat", coat.getItemId());
		assertEquals("Reinforced Full-Length Leather Coat (AT8)", coat.getName());
		assertEquals(ItemType.ARMOR_PIECE, coat.getType());
		assertEquals(0, new BigDecimal("11").compareTo(coat.getWeight()));

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

	private CharacterCreation readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreation.class).readValue(in);
		}
	}
}
