package org.labcabrera.rolemaster.core.tactical.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
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
class BasicCombatTest extends AbstractBasicCombatTest {

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void test() {
		TacticalRound round01 = tacticalService.startRound(ts.getId()).share().block();

		TacticalAction a01 = tacticalActionService.delare(TacticalActionMeleeAttackDeclaration.builder()
			.roundId(round01.getId())
			.priority(TacticalActionPhase.NORMAL)
			.actionPercent(80)
			.source(taMelee01.getId())
			.parry(0)
			.meleeAttackType(MeleeAttackType.PRESS_AND_MELEE)
			.build()).share().block();
		assertNotNull(a01);
		assertNotNull(a01.getState());
		assertEquals(TacticalActionState.PENDING, a01.getState());

		round01 = tacticalService.startInitiativeDeclaration(round01.getId()).share().block();
		round01 = tacticalService.setInitiative(round01.getId(), taMelee01.getId(), 11).share().block();
		round01 = tacticalService.startExecutionPhase(round01.getId()).share().block();

		MeleeAttackExecution meleeAttackExecution = MeleeAttackExecution.builder()
			.targets(Collections.singletonMap(AttackTargetType.MAIN_HAND, taMelee02.getId()))
			.rolls(Collections.singletonMap(AttackTargetType.MAIN_HAND, OpenRoll.of(80)))
			.build();

		TacticalActionMeleeAttack resolved = (TacticalActionMeleeAttack) tacticalActionService.execute(a01.getId(), meleeAttackExecution)
			.share().block();

		assertNotNull(resolved);
		assertEquals(TacticalActionState.RESOLVED, resolved.getState());

		Map<OffensiveBonusModifier, Integer> bonusMap = resolved.getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND);
		// Skill                   +40
		// Action percent:         -20
		// BD                      -30
		assertEquals(-10, bonusMap.values().stream().filter(e -> e != 0).reduce(0, (a, b) -> a + b));
		assertEquals(40, bonusMap.get(OffensiveBonusModifier.SKILL));
		assertEquals(-20, bonusMap.get(OffensiveBonusModifier.ACTION_PERCENT));
		assertEquals(-30, bonusMap.get(OffensiveBonusModifier.DEFENSIVE_BONUS));

		assertEquals(6, resolved.getAttackResults().get(AttackTargetType.MAIN_HAND).getHp());
	}

}
