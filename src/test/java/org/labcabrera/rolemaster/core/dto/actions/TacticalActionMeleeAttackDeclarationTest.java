package org.labcabrera.rolemaster.core.dto.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled("Pending model refactor")
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
			.targets(Collections.singletonMap(AttackTargetType.MAIN_HAND, "target-01"))
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
