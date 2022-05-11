package org.labcabrera.rolemaster.core.service.character.creation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.service.character.CharacterAddSkillService;
import org.labcabrera.rolemaster.core.service.character.CharacterUpdateSkillService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class CharacterCreationService02Test {

	@Autowired
	private CharacterCreationService creationService;

	@Autowired
	private CharacterAddSkillService addSkillService;

	@Autowired
	private CharacterUpdateSkillService skillUpdateService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreation() throws IOException {

		String json = """
			{
				"name": "Pieterman",
				"universeId": "generic",
				"level": 1,
				"raceId": "hillman",
				"professionId": "layman",
				"realm": "channeling",
				"age": 25,
				"height": 184,
				"weight": 82,
				"attributesRoll": 660,
				"baseAttributes": {
					"ag": 96,
					"co": 90,
					"me": 38,
					"re": 43,
					"sd": 39,
					"em": 20,
					"in": 90,
					"pr": 50,
					"st": 92,
					"qu": 75
				},
				"weaponCategoryPriority": [
					"weapon-1h-edged",
					"weapon-thrown",
					"weapon-missile",
					"weapon-1h-concussion",
					"weapon-2h",
					"weapon-pole-arms",
					"weapon-missile-artillery"
				]
			}
			""";

		JwtAuthenticationToken auth = Mockito.mock(JwtAuthenticationToken.class);
		Mockito.when(auth.getName()).thenReturn("test");

		CharacterCreation request = objectMapper.readerFor(CharacterCreation.class).readValue(json);
		CharacterInfo character = creationService.create(auth, request).share().block();

		List<AddSkill> addSkills = Arrays.asList(
			AddSkill.builder().skillId("short-sword").build(),
			AddSkill.builder().skillId("dagger").build(),
			AddSkill.builder().skillId("soft-leather").build(),
			AddSkill.builder().skillId("play-instrument").customizations(Arrays.asList("guitar")).build(),
			AddSkill.builder().skillId("riding").customizations(Arrays.asList("horse")).build(),
			AddSkill.builder().skillId("seduction").build(),
			AddSkill.builder().skillId("situational-awareness").customizations(Arrays.asList("seduction")).build(),
			AddSkill.builder().skillId("history").customizations(Arrays.asList("gondor")).build(),
			AddSkill.builder().skillId("music").build(),
			AddSkill.builder().skillId("painting").build(),
			AddSkill.builder().skillId("cooking-baking").build(),
			AddSkill.builder().skillId("cooking-distilling").build(),
			AddSkill.builder().skillId("leather-crafts-cobbler").build(),
			AddSkill.builder().skillId("leather-crafts-tanner").build(),
			AddSkill.builder().skillId("wood-crafts-carpentry").build(),
			AddSkill.builder().skillId("fauna-lore").build(),
			AddSkill.builder().skillId("survival").customizations(Arrays.asList("hills")).build());
		for (AddSkill e : addSkills) {
			character = addSkillService.addSkill(character.getId(), e).share().block();
		}

		json = """
			{
				"categoryRanks": {
					"armor-light": 1,
					"weapon-1h-edged": 1,
					"artistic-active": 1,
					"artistic-passive": 1,
					"influence": 1
				},
				"skillRanks": {
					"short-sword": 2,
					"dagger": 1,
					"soft-leather": 1,
					"play-instrument:guitar": 1,
					"riding:horse": 1,
					"seduction": 1,
					"situational-awareness:seduction": 1,
					"music": 1,
					"painting": 1,
					"history:gondor": 1,
					"cooking-baking": 1,
					"cooking-distilling": 1,
					"cooking-distilling": 1,
					"leather-crafts-cobbler": 1,
					"leather-crafts-tanner": 1,
					"wood-crafts-carpentry": 1,
					"fauna-lore": 1,
					"survival:hills": 1
				}
			}""";

		SkillUpgrade updateSkillRequest = objectMapper.readerFor(SkillUpgrade.class).readValue(json);
		character = skillUpdateService.updateRanks(auth, character.getId(), updateSkillRequest).share().block();

		assertEquals(2, character.getSkill("short-sword").get().getRanks().get(RankType.DEVELOPMENT));

		File folder = new File("build/characters");
		if (!folder.exists()) {
			folder.mkdirs();
		}
		File file = new File(folder, "creation-test-02.json");
		OutputStream out = new FileOutputStream(file);
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(out, character);
	}

}
