package org.labcabrera.rolemaster.core.character.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.service.character.CharacterAddSkillService;
import org.labcabrera.rolemaster.core.service.character.CharacterUpdateSkillService;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class CharacterCreationService01Test {

	@Autowired
	private CharacterCreationService service;

	@Autowired
	private CharacterAddSkillService addSkillService;

	@Autowired
	private CharacterUpdateSkillService skillUpdateService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CharacterInfoRepository characterRepository;

	@Test
	void testCreation() throws IOException {
		CharacterCreationRequest request = readRequest();

		CharacterInfo characterInfo = service.create(request).share().block();

		assertEquals(61, characterInfo.getDevelopmentPoints().getTotalPoints());
		assertEquals(0, characterInfo.getDevelopmentPoints().getUsedPoints());
		assertFalse(characterInfo.getSkill("short-sword").isPresent());

	}

	private CharacterCreationRequest readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreationRequest.class).readValue(in);
		}
	}

}
