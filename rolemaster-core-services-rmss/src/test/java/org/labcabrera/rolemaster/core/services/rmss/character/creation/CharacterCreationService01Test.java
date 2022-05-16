package org.labcabrera.rolemaster.core.services.rmss.character.creation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.services.character.CharacterAddSkillService;
import org.labcabrera.rolemaster.core.services.character.creation.CharacterCreationService;
import org.labcabrera.rolemaster.core.services.rmss.MockAuthentication;
import org.labcabrera.rolemaster.core.services.rmss.character.CharacterUpdateSkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class CharacterCreationService01Test {

	@Autowired
	private CharacterCreationService service;

	@Autowired
	private CharacterAddSkillService addSkillService;

	@Autowired
	private CharacterUpdateSkillServiceImpl skillUpdateService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CharacterInfoRepository characterRepository;

	@Test
	void testCreation() throws IOException {
		CharacterCreation request = readRequest();

		JwtAuthenticationToken auth = MockAuthentication.mock();

		CharacterInfo characterInfo = service.create(auth, request).share().block();

		assertEquals(61, characterInfo.getDevelopmentPoints().getTotalPoints());
		assertEquals(0, characterInfo.getDevelopmentPoints().getUsedPoints());
		assertFalse(characterInfo.getSkill("short-sword").isPresent());

		AddSkill addSkill = AddSkill.builder()
			.skillId("short-sword")
			.build();

		characterInfo = addSkillService.addSkill(auth, characterInfo.getId(), addSkill).share().block();
		characterInfo = characterRepository.findById(characterInfo.getId()).share().block();
		assertTrue(characterInfo.getSkill("short-sword").isPresent());
		assertEquals(0, characterInfo.getSkill("short-sword").get().getTotalRanks());
		assertEquals(2, characterInfo.getSkill("body-development").get().getTotalRanks());
		assertEquals(Arrays.asList(3, 3, 3), characterInfo.getSkillCategory("armor-medium").get().getDevelopmentCost());
		assertEquals(Arrays.asList(5, 12), characterInfo.getSkill("body-development").get().getDevelopmentCost());
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

		// Cost: 22 = (3 + 3) + (2) + (5) + (2 + 7)

		SkillUpgrade updateSkillRequest = objectMapper.readerFor(SkillUpgrade.class).readValue(json);

		characterInfo = skillUpdateService.updateRanks(auth, characterInfo.getId(), updateSkillRequest).share().block();
		characterInfo = characterRepository.findById(characterInfo.getId()).share().block();
		assertEquals(22, characterInfo.getDevelopmentPoints().getUsedPoints());
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

		// Cost: 12

		updateSkillRequest = objectMapper.readerFor(SkillUpgrade.class).readValue(json);

		characterInfo = skillUpdateService.updateRanks(auth, characterInfo.getId(), updateSkillRequest).share().block();
		characterInfo = characterRepository.findById(characterInfo.getId()).share().block();
		assertEquals(4, characterInfo.getSkill("body-development").get().getTotalRanks());
		assertEquals(34, characterInfo.getDevelopmentPoints().getUsedPoints());

		addSkill = AddSkill.builder()
			.skillId("two-weapon-combat")
			.customizations(Arrays.asList("short-sword", "short-sword"))
			.build();

		String checkSkill = "two-weapon-combat:short-sword:short-sword";

		characterInfo = addSkillService.addSkill(auth, characterInfo.getId(), addSkill).share().block();
		characterInfo = characterRepository.findById(characterInfo.getId()).share().block();
		assertTrue(characterInfo.getSkill(checkSkill).isPresent());
		assertEquals(Arrays.asList("short-sword", "short-sword"), characterInfo.getSkill(checkSkill).get().getCustomization());
		assertEquals(Arrays.asList(4, 12), characterInfo.getSkill(checkSkill).get().getDevelopmentCost());
	}

	private CharacterCreation readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreation.class).readValue(in);
		}
	}

}
