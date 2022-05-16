package org.labcabrera.rolemaster.core.dto.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.action.MovementPace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled("Pending model refactor")
@SpringBootTest
class TacticalActionMovementDeclarationTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test() throws JsonProcessingException {
		TacticalActionMovementDeclaration action = TacticalActionMovementDeclaration.builder()
			.actionPercent(66)
			.priority(TacticalActionPhase.SNAP)
			.source("source-01")
			.pace(MovementPace.FAST_WALK)
			.notes("foo")
			.build();

		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(action);
		String check = """
			{
			  "type" : "movement",
			  "priority" : "snap",
			  "actionPercent" : 66,
			  "source" : "source-01",
			  "pace" : "fastWalk",
			  "notes" : "foo"
			}""";

		assertEquals(check, json);

		TacticalActionDeclaration declaration = objectMapper.readerFor(TacticalActionDeclaration.class).readValue(json);

		assertEquals(declaration, action);
	}

}
