package org.labcabrera.rolemaster.core.services.rmss.tactical.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.dto.tactical.InitiativeDeclaration;
import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterInitiativeDeclaration;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
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

		InitiativeDeclaration initiativeDeclaration = InitiativeDeclaration.builder().build();
		initiativeDeclaration.getCharacters().add(TacticalCharacterInitiativeDeclaration.builder()
			.characterId(taMelee01.getId())
			.initiativeRoll(11)
			.build());
		round01 = tacticalInitiativeService.initiativeDeclaration(auth, ts.getId(), initiativeDeclaration).share().block();

		List<TacticalAction> actionQueue = tacticalService.getActionQueue(r01Id).share().collectList().share().block();
		assertEquals(1, actionQueue.size());

		MeleeAttackExecution meleeAttackExecution = MeleeAttackExecution.builder()
			.targets(Collections.singletonMap(AttackTargetType.MAIN_HAND, taMelee02.getId()))
			.rolls(Collections.singletonMap(AttackTargetType.MAIN_HAND, OpenRoll.of(95)))
			.build();

		TacticalActionMeleeAttack meleeResolved01 = (TacticalActionMeleeAttack) tacticalActionService
			.execute(a01.getId(), meleeAttackExecution).share().block();

		Map<OffensiveBonusModifier, Integer> mainHandBonusMap = meleeResolved01.getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND);
		assertEquals(40, mainHandBonusMap.get(OffensiveBonusModifier.SKILL));
		assertEquals(-10, mainHandBonusMap.get(OffensiveBonusModifier.ACTION_PERCENT));
		assertEquals(-30, mainHandBonusMap.get(OffensiveBonusModifier.DEFENSIVE_BONUS));
		assertEquals(0, mainHandBonusMap.values().stream().reduce(0, (a, b) -> a + b));

		assertEquals(10, meleeResolved01.getAttackResults().get(AttackTargetType.MAIN_HAND).getHp());
		assertEquals(1, meleeResolved01.getCriticalResults().size());
		assertEquals(CriticalSeverity.A, meleeResolved01.getCriticalResults().get(AttackTargetType.MAIN_HAND).get(0).getSeverity());
		assertEquals(CriticalType.S, meleeResolved01.getCriticalResults().get(AttackTargetType.MAIN_HAND).get(0).getType());

		assertEquals(TacticalActionState.PENDING_CRITICAL_RESOLUTION, meleeResolved01.getState());

		AttackCriticalExecution criticalExecution = AttackCriticalExecution.builder()
			.rolls(Collections.singletonMap("roll-1", 55))
			.build();

		TacticalAction taResolved02 = tacticalActionService.executeCritical(meleeResolved01.getId(), criticalExecution).share().block();
		assertTrue(taResolved02 instanceof TacticalActionMeleeAttack);
		TacticalActionMeleeAttack meleeResolved02 = (TacticalActionMeleeAttack) taResolved02;

		assertEquals(TacticalActionState.RESOLVED, meleeResolved02.getState());
		assertEquals(2, meleeResolved02.getCriticalResults().get(AttackTargetType.MAIN_HAND).get(0).getCriticalTableResult().getHp());
		assertEquals(55, meleeResolved02.getCriticalResults().get(AttackTargetType.MAIN_HAND).get(0).getRoll());

		taMelee02 = this.tacticalCharacterRepository.findById(taMelee02.getId()).share().block();
		assertEquals(38, taMelee02.getHp().getCurrent());
		assertEquals(1, taMelee02.getCombatStatus().getDebuffs().size());
		assertEquals(2, taMelee02.getCombatStatus().getDebuffs().get(Debuff.MUST_PARRY));

	}

}
