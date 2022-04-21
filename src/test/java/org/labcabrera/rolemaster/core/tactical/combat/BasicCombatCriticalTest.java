package org.labcabrera.rolemaster.core.tactical.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicCombatCriticalTest extends AbstractBasicCombatTest {

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void test() {
		assertEquals(50, taMelee02.getHp().getMax());
		assertEquals(50, taMelee02.getHp().getCurrent());

		TacticalRound round01 = tacticalService.startRound(ts.getId()).share().block();
		String r01Id = round01.getId();

		TacticalAction a01 = tacticalActionService.delare(TacticalActionMeleeAttackDeclaration.builder()
			.roundId(r01Id)
			.priority(TacticalActionPhase.NORMAL)
			.actionPercent(80)
			.source(taMelee01.getId())
			.parry(0)
			.meleeAttackType(MeleeAttackType.PRESS_AND_MELEE)
			.build()).share().block();
		assertNotNull(a01);
		assertNotNull(a01.getState());
		assertEquals(TacticalActionState.PENDING, a01.getState());

		round01 = tacticalService.startInitiativeDeclaration(r01Id).share().block();

		round01 = tacticalService.setInitiative(r01Id, taMelee01.getId(), 11).share().block();

		round01 = tacticalService.startExecutionPhase(r01Id).share().block();

		List<TacticalAction> actionQueue = tacticalService.getActionQueue(r01Id).share().collectList().share().block();
		assertEquals(1, actionQueue.size());

		MeleeAttackExecution meleeAttackExecution = MeleeAttackExecution.builder()
			.targets(Collections.singletonMap(AttackTargetType.MAIN_HAND, taMelee02.getId()))
			.rolls(Collections.singletonMap(AttackTargetType.MAIN_HAND, OpenRoll.of(105)))
			.build();

		TacticalAction taResolved01 = tacticalActionService.execute(a01.getId(), meleeAttackExecution).share().block();
		assertTrue(taResolved01 instanceof TacticalActionMeleeAttack);
		TacticalActionMeleeAttack meleeResolved01 = (TacticalActionMeleeAttack) taResolved01;

		assertEquals(10, meleeResolved01.getAttackResults().get(0).getHp());
		assertEquals(-10,
			meleeResolved01.getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).values().stream().reduce(0, (a, b) -> a + b));
		assertEquals(1, meleeResolved01.getCriticalResults().size());
		assertEquals(CriticalSeverity.A, meleeResolved01.getCriticalResults().get(0).getSeverity());
		assertEquals(CriticalType.S, meleeResolved01.getCriticalResults().get(0).getType());

		assertEquals(TacticalActionState.PENDING_CRITICAL_RESOLUTION, meleeResolved01.getState());

		AttackCriticalExecution criticalExecution = AttackCriticalExecution.builder()
			.rolls(Collections.singletonMap("roll-1", 55))
			.build();

		TacticalAction taResolved02 = tacticalActionService.executeCritical(taResolved01.getId(), criticalExecution).share().block();
		assertTrue(taResolved02 instanceof TacticalActionMeleeAttack);
		TacticalActionMeleeAttack meleeResolved02 = (TacticalActionMeleeAttack) taResolved02;

		assertEquals(TacticalActionState.RESOLVED, meleeResolved02.getState());
		assertEquals(2, meleeResolved02.getCriticalResults().get(0).getCriticalTableResult().getHp());
		assertEquals(55, meleeResolved02.getCriticalResults().get(0).getRoll());

		taMelee02 = this.tacticalCharacterRepository.findById(taMelee02.getId()).share().block();
		assertEquals(38, taMelee02.getHp().getCurrent());
		assertEquals(1, taMelee02.getCombatStatus().getDebuffs().size());
		assertEquals(1, taMelee02.getCombatStatus().getDebuffs().get(Debuff.MUST_PARRY));

	}

}
