package org.labcabrera.rolemaster.core.character;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CharacterCreationServiceWoodElvesHealerTest extends AbstractCharacterCreationTest {

	@Override
	protected String getRequestAsJson() {
		return """
				{
				"name": "Test Wood Elves Healer",
				"level": 1,
				"raceId": "wood-elves",
				"professionId": "healer",
				"realmId": "essence",
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

}
