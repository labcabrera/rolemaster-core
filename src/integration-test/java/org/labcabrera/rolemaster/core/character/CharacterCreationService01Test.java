package org.labcabrera.rolemaster.core.character;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.RankType;
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

		AddSkill addSkill = AddSkill.builder()
			.skillId("short-sword")
			.build();

		characterInfo = addSkillService.addSkill(characterInfo.getId(), addSkill).share().block();
		characterInfo = characterRepository.findById(characterInfo.getId()).share().block();
		assertTrue(characterInfo.getSkill("short-sword").isPresent());
		assertEquals(0, characterInfo.getSkill("short-sword").get().getTotalRanks());
		assertEquals(2, characterInfo.getSkill("body-development").get().getTotalRanks());
		assertEquals(Arrays.asList(2, 2, 2), characterInfo.getSkillCategory("armor-medium").get().getDevelopmentCost());
		assertEquals(Arrays.asList(2, 5), characterInfo.getSkill("body-development").get().getDevelopmentCost());
		assertEquals(Arrays.asList(2, 7), characterInfo.getSkill("short-sword").get().getDevelopmentCost());

		String json = """
			{
				"categoryRanks": {
					"armor-medium": 2,
					"weapon-1h-edged": 1
				},
				"skillRanks": {
					"body-development": 1,
					"short-sword": 2
				}
			}""";

		// Cost: (2 + 2) + (2) + (2) + (2 + 7)

		SkillUpgrade updateSkillRequest = objectMapper.readerFor(SkillUpgrade.class).readValue(json);

		characterInfo = skillUpdateService.updateRanks(characterInfo.getId(), updateSkillRequest).share().block();
		characterInfo = characterRepository.findById(characterInfo.getId()).share().block();
		assertEquals(17, characterInfo.getDevelopmentPoints().getUsedPoints());
		assertEquals(2, characterInfo.getSkill("short-sword").get().getTotalRanks());
		assertEquals(2, characterInfo.getSkill("short-sword").get().getRanks().get(RankType.DEVELOPMENT));
		assertEquals(0, characterInfo.getSkill("short-sword").get().getRanks().get(RankType.CONSOLIDATED));
		assertEquals(0, characterInfo.getSkill("short-sword").get().getRanks().get(RankType.ADOLESCENCE));
		assertEquals(3, characterInfo.getSkill("body-development").get().getTotalRanks());

		json = """
			{
				"skillRanks": {
					"body-development": 1
				}
			}""";

		// Cost: 5

		updateSkillRequest = objectMapper.readerFor(SkillUpgrade.class).readValue(json);

		characterInfo = skillUpdateService.updateRanks(characterInfo.getId(), updateSkillRequest).share().block();
		characterInfo = characterRepository.findById(characterInfo.getId()).share().block();
		assertEquals(4, characterInfo.getSkill("body-development").get().getTotalRanks());
		assertEquals(22, characterInfo.getDevelopmentPoints().getUsedPoints());

		addSkill = AddSkill.builder()
			.skillId("two-weapon-combat")
			.customizations(Arrays.asList("short-sword", "short-sword"))
			.build();

		String checkSkill = "two-weapon-combat:short-sword:short-sword";

		characterInfo = addSkillService.addSkill(characterInfo.getId(), addSkill).share().block();
		characterInfo = characterRepository.findById(characterInfo.getId()).share().block();
		assertTrue(characterInfo.getSkill(checkSkill).isPresent());
		assertEquals(Arrays.asList("short-sword", "short-sword"), characterInfo.getSkill(checkSkill).get().getCustomization());
		assertEquals(Arrays.asList(3, 9), characterInfo.getSkill(checkSkill).get().getDevelopmentCost());
	}

	private CharacterCreationRequest readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreationRequest.class).readValue(in);
		}
	}

}
