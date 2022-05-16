package org.labcabrera.rolemaster.core.services.rmss.tactical.maneuvers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.MovementPace;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.services.rmss.MockNpcIdentifier;
import org.labcabrera.rolemaster.core.services.rmss.tactical.AbstractTacticalTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovementManeuverTest extends AbstractTacticalTest {

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void testMovement() {
		TacticalRound round = tacticalService.startRound(ts.getId()).share().block();

		TacticalCharacter tc = tacticalService.addNpc(ts.getId(), MockNpcIdentifier.ORC_SCIMITAR_II, NpcCustomization.builder()
			.name("orc-01").build())
			.share().block();

		TacticalAction a01 = tacticalActionService.delare(TacticalActionMovementDeclaration.builder()
			.roundId(round.getId())
			.source(tc.getId())
			.pace(MovementPace.RUN)
			.actionPercent(20)
			.priority(TacticalActionPhase.SNAP)
			.build()).share().block();

		assertNotNull(a01);
		assertNotNull(a01.getState());
		assertEquals(TacticalActionState.PENDING, a01.getState());

		//TODO check

	}

}
