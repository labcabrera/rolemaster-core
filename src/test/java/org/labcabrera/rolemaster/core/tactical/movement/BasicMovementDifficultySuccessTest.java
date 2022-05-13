package org.labcabrera.rolemaster.core.tactical.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MovementExecution;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.MovementPace;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMovement;
import org.labcabrera.rolemaster.core.tactical.combat.AbstractBasicCombatTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicMovementDifficultySuccessTest extends AbstractBasicCombatTest {

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void test() {
		TacticalRound round01 = tacticalService.startRound(ts.getId()).share().block();

		TacticalActionMovementDeclaration declaration = TacticalActionMovementDeclaration.builder()
			.roundId(round01.getId())
			.actionPercent(80)
			.pace(MovementPace.DASH)
			.priority(TacticalActionPhase.DELIBERATE)
			.source(this.taMelee01.getId())
			.build();

		TacticalActionMovement movementDeclared = (TacticalActionMovement) tacticalActionService.delare(declaration).share().block();
		assertEquals(TacticalActionState.PENDING, movementDeclared.getState());

		round01 = tacticalService.startInitiativeDeclaration(round01.getId()).share().block();
		round01 = tacticalService.setInitiative(round01.getId(), taMelee01.getId(), 11).share().block();
		round01 = tacticalService.startExecutionPhase(round01.getId()).share().block();

		MovementExecution execution = MovementExecution.builder()
			.difficulty(ManeuverDifficulty.MEDIUM)
			.customModifier(15)
			.roll(25)
			.build();

		TacticalActionMovement resolved = (TacticalActionMovement) tacticalActionService.execute(movementDeclared.getId(), execution)
			.share().block();

		assertNotNull(resolved);
		assertEquals(TacticalActionState.RESOLVED, resolved.getState());

		assertEquals(15, taMelee01.getBaseMovementRate());
		assertEquals(0.5, ts.getScale());
		assertEquals(12, resolved.getDistance());
		assertEquals(6, resolved.getDistanceScaled());
	}

}
