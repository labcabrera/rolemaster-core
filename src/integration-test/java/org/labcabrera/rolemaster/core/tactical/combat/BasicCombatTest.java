package org.labcabrera.rolemaster.core.tactical.combat;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackPosition;
import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicCombatTest extends AbstractBasicCombatTest {

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void test() {
		TacticalRound round01 = tacticalService.startRound(ts.getId()).share().block();
		String r01Id = round01.getId();

		TacticalAction a01 = tacticalActionService.delare(TacticalActionMeleeAttackDeclaration.builder()
			.roundId(r01Id)
			.priority(TacticalActionPhase.NORMAL)
			.actionPercent(80)
			.source(cc01.getId())
			.parry(0)
			.meleeAttackType(MeleeAttackType.PRESS_AND_MELEE)
			.build()).share().block();
		assertNotNull(a01);
		assertNotNull(a01.getState());
		assertEquals(TacticalActionState.PENDING, a01.getState());

		round01 = tacticalService.startInitiativeDeclaration(r01Id).share().block();

		round01 = tacticalService.setInitiative(r01Id, cc01.getId(), 11).share().block();

		round01 = tacticalService.startExecutionPhase(r01Id).share().block();

		List<TacticalAction> actionQueue = tacticalService.getActionQueue(r01Id).share().collectList().share().block();
		assertEquals(1, actionQueue.size());

		MeleeAttackExecution meleeAttackExecution = MeleeAttackExecution.builder()
			.actionId(a01.getId())
			.primaryTarget(cc02.getId())
			.position(MeleeAttackPosition.NORMAL)
			.primaryRoll(OpenRoll.of(60))
			.build();

		TacticalAction taResolved01 = tacticalActionService.execute(meleeAttackExecution).share().block();
		assertTrue(taResolved01 instanceof TacticalActionMeleeAttack);
		TacticalActionMeleeAttack meleeResolved01 = (TacticalActionMeleeAttack) taResolved01;

		assertNotNull(meleeResolved01);
		assertEquals(TacticalActionState.RESOLVED, meleeResolved01.getState());
		assertEquals(6, meleeResolved01.getPrimaryAttackResult().getHpResult());
	}

}
