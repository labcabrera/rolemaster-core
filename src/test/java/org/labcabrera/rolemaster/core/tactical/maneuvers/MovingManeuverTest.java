package org.labcabrera.rolemaster.core.tactical.maneuvers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMovingManeuverDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MovingManeuverExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovingManeuver;
import org.labcabrera.rolemaster.core.tactical.AbstractTacticalTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovingManeuverTest extends AbstractTacticalTest {

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void testMovement() {
		TacticalRound round = tacticalService.startRound(ts.getId()).share().block();

		TacticalCharacter tc = tacticalService.addNpc(ts.getId(), "orc-fighter-scimitar-ii", NpcCustomization.builder()
			.name("orc-01").build())
			.share().block();

		TacticalActionMovingManeuverDeclaration declaration = TacticalActionMovingManeuverDeclaration.builder()
			.roundId(round.getId())
			.source(tc.getId())
			.actionPercent(100)
			.skillId("jumping")
			.priority(TacticalActionPhase.DELIBERATE)
			.build();

		TacticalAction actionDeclared = tacticalActionService.delare(declaration).share().block();

		assertNotNull(actionDeclared);
		assertNotNull(actionDeclared.getState());
		assertEquals(TacticalActionState.PENDING, actionDeclared.getState());

		MovingManeuverExecution execution = MovingManeuverExecution.builder()
			.dificulty(ManeuverDificulty.MEDIUM)
			.roll(OpenRoll.of(50))
			.build();

		TacticalActionMovingManeuver actionExecuted = (TacticalActionMovingManeuver) tacticalActionService
			.execute(actionDeclared.getId(), execution).share().block();

		assertNotNull(actionExecuted);
		assertNotNull(actionExecuted.getModifiers());
		assertNotNull(actionExecuted.getResult());
		assertNotNull(actionExecuted.getRoll());
		assertEquals(TacticalActionState.RESOLVED, actionExecuted.getState());

	}

}
