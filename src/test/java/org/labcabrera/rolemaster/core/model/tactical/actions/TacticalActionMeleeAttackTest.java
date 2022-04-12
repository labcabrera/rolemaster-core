package org.labcabrera.rolemaster.core.model.tactical.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled("Pending model refactor")
@SpringBootTest
class TacticalActionMeleeAttackTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test() throws JsonProcessingException {
		TacticalActionMeleeAttack action = TacticalActionMeleeAttack.builder()
			.actionPercent(42)
			.meleeAttackType(MeleeAttackType.PRESS_AND_MELEE)
			.parry(55)
			.priority(TacticalActionPhase.NORMAL)
			.source("source-01")
			.target("target-01")
			.state(TacticalActionState.PENDING)
			.notes("foo")
			.build();
		action.getInitiativeModifiers().put(InitiativeModifier.CUSTOM, 10);
		action.getInitiativeModifiers().put(InitiativeModifier.ROLL, 11);

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
		assertTrue(actionReaded instanceof TacticalActionMeleeAttack);

		assertEquals(action, actionReaded);

		actionReaded.getInitiativeModifiers().put(InitiativeModifier.HP, -5);

		assertNotEquals(action, actionReaded);
	}

}
