package org.labcabrera.rolemaster.core.dto.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class TacticalActionMeleeAttackDeclarationTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test() throws JsonProcessingException {
		TacticalActionMeleeAttackDeclaration action = TacticalActionMeleeAttackDeclaration.builder()
			.actionPercent(66)
			.priority(TacticalActionPhase.SNAP)
			.source("source-01")
			.meleeAttackType(MeleeAttackType.FULL)
			.parry(55)
			.target("target-01")
			.notes("foo")
			.build();

		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(action);
		String check = """
			{
			  "type" : "melee-attack",
			  "priority" : "snap",
			  "actionPercent" : 66,
			  "source" : "source-01",
			  "target" : "target-01",
			  "meleeAttackType" : "full",
			  "parry" : 55,
			  "notes" : "foo"
			}""";

		assertEquals(check, json);

		TacticalActionDeclaration declaration = objectMapper.readerFor(TacticalActionDeclaration.class).readValue(json);

		assertEquals(declaration, action);
	}

}
