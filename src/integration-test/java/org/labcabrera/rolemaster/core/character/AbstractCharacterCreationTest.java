package org.labcabrera.rolemaster.core.character;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractCharacterCreationTest {

	@Autowired
	protected CharacterCreationService service;

	@Autowired
	protected ObjectMapper objectMapper;

	@Test
	void testCreation() throws IOException {
		String json = getRequestAsJson();

		CharacterCreationRequest request = objectMapper.readerFor(CharacterCreationRequest.class).readValue(json);

		CharacterInfo characterInfo = service.create(request).share().block();

		for (CharacterSkillCategory category : characterInfo.getSkillCategories()) {
			assertNotNull(category.getDevelopmentCost(), "Missing dev points for category " + category.getCategoryId());
		}
		for (CharacterSkill skill : characterInfo.getSkills()) {
			assertNotNull(skill.getDevelopmentCost(), "Missing dev points for skill " + skill.getCategoryId());
		}
		verify(characterInfo);
	}

	protected abstract String getRequestAsJson();

	protected void verify(CharacterInfo character) {
		// Override for additional validations
	}
}
