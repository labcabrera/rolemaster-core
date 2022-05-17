package org.labcabrera.rolemaster.core.services.rmss.character.creation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.labcabrera.rolemaster.core.model.character.AttributeBonusType;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.ResistanceBonusType;
import org.labcabrera.rolemaster.core.model.character.ResistanceType;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CharacterCreationServiceGreyElvesDabblerTest extends AbstractCharacterCreationTest {

	@Override
	protected String getRequestAsJson() {
		return """
				{
				"name": "Test Grey Elves Dabbler",
				"version": "rmss",
				"universeId": "generic",
				"level": 1,
				"raceId": "grey-elf",
				"professionId": "dabbler",
				"realm": "essence",
				"age": 41,
				"height": 174,
				"weight": 72,
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
			}""";
	}

	@Override
	protected void verify(CharacterInfo character) {
		assertEquals(2, character.getAttributes().get(AttributeType.AGILITY).getBonus().get(AttributeBonusType.RACE));
		assertEquals(0, character.getAttributes().get(AttributeType.CONSTITUTION).getBonus().get(AttributeBonusType.RACE));
		assertEquals(2, character.getAttributes().get(AttributeType.MEMORY).getBonus().get(AttributeBonusType.RACE));
		assertEquals(0, character.getAttributes().get(AttributeType.REASONING).getBonus().get(AttributeBonusType.RACE));
		assertEquals(-5, character.getAttributes().get(AttributeType.SELF_DISCIPLINE).getBonus().get(AttributeBonusType.RACE));
		assertEquals(2, character.getAttributes().get(AttributeType.EMPATHY).getBonus().get(AttributeBonusType.RACE));
		assertEquals(0, character.getAttributes().get(AttributeType.INTUTITION).getBonus().get(AttributeBonusType.RACE));
		assertEquals(4, character.getAttributes().get(AttributeType.PRESENCE).getBonus().get(AttributeBonusType.RACE));
		assertEquals(4, character.getAttributes().get(AttributeType.QUICKNESS).getBonus().get(AttributeBonusType.RACE));
		assertEquals(0, character.getAttributes().get(AttributeType.STRENGTH).getBonus().get(AttributeBonusType.RACE));

		assertEquals(-5, character.getResistances().get(ResistanceType.CHANNELING).getBonus().get(ResistanceBonusType.RACE));
		assertEquals(-5, character.getResistances().get(ResistanceType.ESSENCE).getBonus().get(ResistanceBonusType.RACE));
		assertEquals(-5, character.getResistances().get(ResistanceType.MENTALISM).getBonus().get(ResistanceBonusType.RACE));
		assertEquals(10, character.getResistances().get(ResistanceType.POISON).getBonus().get(ResistanceBonusType.RACE));
		assertEquals(100, character.getResistances().get(ResistanceType.DISEASE).getBonus().get(ResistanceBonusType.RACE));

		assertEquals(Arrays.asList(6, 14), character.getSkillCategory("body-development").get().getDevelopmentCost());
		assertEquals(Arrays.asList(8), character.getSkillCategory("power-point-development").get().getDevelopmentCost());

		assertEquals(Arrays.asList(3, 9), character.getSkillCategory("weapon-1h-edged").get().getDevelopmentCost());
		assertEquals(Arrays.asList(6), character.getSkillCategory("weapon-thrown").get().getDevelopmentCost());
		assertEquals(Arrays.asList(7), character.getSkillCategory("weapon-missile").get().getDevelopmentCost());
		assertEquals(Arrays.asList(8), character.getSkillCategory("weapon-1h-concussion").get().getDevelopmentCost());
		assertEquals(Arrays.asList(10), character.getSkillCategory("weapon-2h").get().getDevelopmentCost());
		assertEquals(Arrays.asList(15), character.getSkillCategory("weapon-pole-arms").get().getDevelopmentCost());
		assertEquals(Arrays.asList(15), character.getSkillCategory("weapon-missile-artillery").get().getDevelopmentCost());

		assertEquals(Arrays.asList(11), character.getSkillCategory("armor-heavy").get().getDevelopmentCost());
		assertEquals(Arrays.asList(10), character.getSkillCategory("armor-medium").get().getDevelopmentCost());
		assertEquals(Arrays.asList(5, 5, 5), character.getSkillCategory("armor-light").get().getDevelopmentCost());

		assertEquals(Arrays.asList(3, 9), character.getSkillCategory("athletic-brawn").get().getDevelopmentCost());
		assertEquals(Arrays.asList(2, 7), character.getSkillCategory("athletic-endurance").get().getDevelopmentCost());
		assertEquals(Arrays.asList(2, 4), character.getSkillCategory("athletic-gymnastics").get().getDevelopmentCost());

		assertEquals(Arrays.asList(4, 12), character.getSkillCategory("awareness-perceptions").get().getDevelopmentCost());
		assertEquals(Arrays.asList(2, 6), character.getSkillCategory("awareness-searching").get().getDevelopmentCost());
		assertEquals(Arrays.asList(3, 7), character.getSkillCategory("awareness-senses").get().getDevelopmentCost());

		assertEquals(Arrays.asList(6, 12), character.getSkillCategory("subterfuge-attack").get().getDevelopmentCost());
		assertEquals(Arrays.asList(1, 5), character.getSkillCategory("subterfuge-stealth").get().getDevelopmentCost());
		assertEquals(Arrays.asList(2, 6), character.getSkillCategory("subterfuge-mechanics").get().getDevelopmentCost());

		assertEquals(Arrays.asList(6, 14), character.getSkillCategory("combat-maneuvers").get().getDevelopmentCost());
	}

}
