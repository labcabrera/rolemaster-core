package org.labcabrera.rolemaster.core.model.tactical.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class TacticalActionMeleAttackTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test() throws JsonProcessingException {
		TacticalActionMeleAttack action = TacticalActionMeleAttack.builder()
			.actionPercent(42)
			.meleAttackType(MeleeAttackType.PRESS_AND_MELEE)
			.parry(55)
			.priority(TacticalActionPhase.NORMAL)
			.source("source-01")
			.target("target-01")
			.state(TacticalActionState.QUEUED)
			.notes("foo")
			.build();
		action.getInitiativeModifiers().put(InitiativeModifier.CUSTOM, 10);
		action.getInitiativeModifiers().put(InitiativeModifier.RANDOM_ROLL, 11);

		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(action);
		String check = """
			{
			  "type" : "melee-attack",
			  "state" : "queued",
			  "priority" : "normal",
			  "actionPercent" : 42,
			  "initiativeModifiers" : {
			    "custom" : 10,
			    "randomRoll" : 11
			  },
			  "initiative" : 21,
			  "source" : "source-01",
			  "target" : "target-01",
			  "meleAttackType" : "pressAndMelee",
			  "parry" : 55,
			  "notes" : "foo"
			}""";

		assertEquals(check, json);
		TacticalAction actionReaded = objectMapper.readerFor(TacticalAction.class).readValue(json);
		assertTrue(actionReaded instanceof TacticalActionMeleAttack);

		assertEquals(action, actionReaded);

		actionReaded.getInitiativeModifiers().put(InitiativeModifier.HP, -5);

		assertNotEquals(action, actionReaded);
	}

}
