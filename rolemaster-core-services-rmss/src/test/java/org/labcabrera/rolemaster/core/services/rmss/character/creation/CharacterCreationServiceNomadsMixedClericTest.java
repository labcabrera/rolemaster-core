package org.labcabrera.rolemaster.core.services.rmss.character.creation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.labcabrera.rolemaster.core.model.character.AttributeBonusType;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.ResistanceBonusType;
import org.labcabrera.rolemaster.core.model.character.ResistanceType;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CharacterCreationServiceNomadsMixedClericTest extends AbstractCharacterCreationTest {

	@Override
	protected String getRequestAsJson() {
		return """
				{
				"name": "Test Nomads Mixed Healer",
				"version": "rmss",
				"universeId": "generic",
				"level": 1,
				"raceId": "nomad-mixed",
				"professionId": "healer",
				"realm": "channeling",
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
		assertEquals(0, character.getAttributes().get(AttributeType.AGILITY).getBonus().get(AttributeBonusType.RACE));
		assertEquals(2, character.getAttributes().get(AttributeType.CONSTITUTION).getBonus().get(AttributeBonusType.RACE));
		assertEquals(0, character.getAttributes().get(AttributeType.MEMORY).getBonus().get(AttributeBonusType.RACE));
		assertEquals(0, character.getAttributes().get(AttributeType.REASONING).getBonus().get(AttributeBonusType.RACE));
		assertEquals(2, character.getAttributes().get(AttributeType.SELF_DISCIPLINE).getBonus().get(AttributeBonusType.RACE));
		assertEquals(2, character.getAttributes().get(AttributeType.EMPATHY).getBonus().get(AttributeBonusType.RACE));
		assertEquals(0, character.getAttributes().get(AttributeType.INTUTITION).getBonus().get(AttributeBonusType.RACE));
		assertEquals(2, character.getAttributes().get(AttributeType.PRESENCE).getBonus().get(AttributeBonusType.RACE));
		assertEquals(0, character.getAttributes().get(AttributeType.QUICKNESS).getBonus().get(AttributeBonusType.RACE));
		assertEquals(2, character.getAttributes().get(AttributeType.STRENGTH).getBonus().get(AttributeBonusType.RACE));

		assertEquals(0, character.getResistances().get(ResistanceType.CHANNELING).getBonus().get(ResistanceBonusType.RACE));
		assertEquals(0, character.getResistances().get(ResistanceType.ESSENCE).getBonus().get(ResistanceBonusType.RACE));
		assertEquals(0, character.getResistances().get(ResistanceType.MENTALISM).getBonus().get(ResistanceBonusType.RACE));
		assertEquals(0, character.getResistances().get(ResistanceType.POISON).getBonus().get(ResistanceBonusType.RACE));
		assertEquals(0, character.getResistances().get(ResistanceType.DISEASE).getBonus().get(ResistanceBonusType.RACE));
	}

}
