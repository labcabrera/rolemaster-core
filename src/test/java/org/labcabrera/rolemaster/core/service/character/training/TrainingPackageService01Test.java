package org.labcabrera.rolemaster.core.service.character.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.MockAuthentication;
import org.labcabrera.rolemaster.core.dto.TrainingPackageUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.service.character.TrainingPackageUpgradeService;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class TrainingPackageService01Test {

	@Autowired
	private CharacterCreationService service;

	@Autowired
	private TrainingPackageUpgradeService traningPackageUpgradeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreation() throws IOException {
		JwtAuthenticationToken auth = MockAuthentication.mock();

		CharacterCreation request = readRequest();

		CharacterInfo character = service.create(auth, request).share().block();

		assertEquals(61, character.getDevelopmentPoints().getTotalPoints());
		assertEquals(0, character.getDevelopmentPoints().getUsedPoints());
		assertFalse(character.getSkill("short-sword").isPresent());

		String json = """
			{
				"trainingPackageId": "assassin",
				"categorySelection": [
					{
						"key": "weapon-category",
						"categoryId": "weapon-1h-edged",
						"ranks": 1
					}
				],
				"skillSelection": [
					{
						"key": "weapon-skill",
						"skillId": "broadsword",
						"ranks": 1
					},
					{
						"key": "subterfuge-stealth-skill",
						"skillId": "hiding",
						"ranks": 1
					},
					{
						"key": "subterfuge-stealth-skill",
						"skillId": "stalking",
						"ranks": 1
					},
					{
						"key": "subterfuge-mechanics-skill",
						"skillId": "using-removing-poison",
						"ranks": 1
					},
					{
						"key": "subterfuge-attack-skill",
						"skillId": "ambush",
						"ranks": 2
					},
					{
						"key": "subterfuge-attack-skill",
						"skillId": "silent-attack",
						"ranks": 1
					},
					{
						"key": "awareness-senses-skill",
						"skillId": "sense-awareness-hear",
						"ranks": 1
					},
					{
						"key": "special-attacks-skill",
						"skillId": "brawling",
						"ranks": 2
					}

				]
			}""";

		TrainingPackageUpgrade tpu = objectMapper.readerFor(TrainingPackageUpgrade.class).readValue(json);

		character = traningPackageUpgradeService.upgrade(character.getId(), tpu).share().block();

		assertTrue(character.getTrainingPackages().containsKey(tpu.getTrainingPackageId()));
		assertEquals(16, character.getDevelopmentPoints().getUsedPoints());
		assertNotNull(character.getSkill("stalking").get());

		assertEquals(2, character.getSkillCategory("subterfuge-stealth").get().getRanks().get(RankType.TRAINING_PACKAGE));
		assertEquals(1, character.getSkillCategory("subterfuge-mechanics").get().getRanks().get(RankType.TRAINING_PACKAGE));
		assertEquals(1, character.getSkillCategory("awareness-senses").get().getRanks().get(RankType.TRAINING_PACKAGE));

		assertEquals(1, character.getSkill("broadsword").get().getRanks().get(RankType.TRAINING_PACKAGE));
		assertEquals(1, character.getSkill("hiding").get().getRanks().get(RankType.TRAINING_PACKAGE));
		assertEquals(1, character.getSkill("stalking").get().getRanks().get(RankType.TRAINING_PACKAGE));
		assertEquals(1, character.getSkill("using-removing-poison").get().getRanks().get(RankType.TRAINING_PACKAGE));
		assertEquals(2, character.getSkill("ambush").get().getRanks().get(RankType.TRAINING_PACKAGE));
		assertEquals(1, character.getSkill("silent-attack").get().getRanks().get(RankType.TRAINING_PACKAGE));
		assertEquals(1, character.getSkill("sense-awareness-hear").get().getRanks().get(RankType.TRAINING_PACKAGE));
		assertEquals(2, character.getSkill("brawling").get().getRanks().get(RankType.TRAINING_PACKAGE));
	}

	private CharacterCreation readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreation.class).readValue(in);
		}
	}

}
