package org.labcabrera.rolemaster.core.tactical.maneuvers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionStaticManeuverDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.StaticManeuverExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionStaticManeuver;
import org.labcabrera.rolemaster.core.tactical.AbstractTacticalTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StaticManeuverTest extends AbstractTacticalTest {

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void testHiding() {
		TacticalRound round = tacticalService.startRound(ts.getId()).share().block();

		TacticalCharacter tc = tacticalService.addNpc(ts.getId(), "orc-fighter-scimitar-ii", NpcCustomization.builder()
			.name("orc-01").build())
			.share().block();

		TacticalActionStaticManeuverDeclaration declaration = TacticalActionStaticManeuverDeclaration.builder()
			.roundId(round.getId())
			.source(tc.getId())
			.actionPercent(100)
			.skillId("hiding")
			.priority(TacticalActionPhase.DELIBERATE)
			.build();

		TacticalAction actionDeclared = tacticalActionService.delare(declaration).share().block();

		assertNotNull(actionDeclared);
		assertNotNull(actionDeclared.getState());
		assertEquals(TacticalActionState.PENDING, actionDeclared.getState());

		StaticManeuverExecution execution = StaticManeuverExecution.builder()
			.difficulty(ManeuverDifficulty.MEDIUM)
			.roll(OpenRoll.of(50))
			.build();

		TacticalActionStaticManeuver actionExecuted = (TacticalActionStaticManeuver) tacticalActionService
			.execute(actionDeclared.getId(), execution).share().block();

		assertNotNull(actionExecuted);
		assertNotNull(actionExecuted.getModifiers());
		assertNotNull(actionExecuted.getResult());
		assertNotNull(actionExecuted.getRoll());
		assertEquals(TacticalActionState.RESOLVED, actionExecuted.getState());

	}

}
