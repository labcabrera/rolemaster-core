package org.labcabrera.rolemaster.core.services.rmss.tactical.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicCombatParryFullAttackTest extends AbstractBasicCombatTest {

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
			.targets(Collections.singletonMap(AttackTargetType.MAIN_HAND, taMelee02.getId()))
			.parry(5)
			.meleeAttackType(MeleeAttackType.PRESS_AND_MELEE)
			.build()).share().block();

		TacticalAction a02 = tacticalActionService.delare(TacticalActionMeleeAttackDeclaration.builder()
			.roundId(r01Id)
			.priority(TacticalActionPhase.NORMAL)
			.actionPercent(80)
			.source(taMelee02.getId())
			.targets(Collections.singletonMap(AttackTargetType.MAIN_HAND, taMelee01.getId()))
			.parry(25)
			.meleeAttackType(MeleeAttackType.FULL)
			.build()).share().block();

		assertNotNull(a01);
		assertNotNull(a02);

		round01 = tacticalService.startInitiativeDeclaration(r01Id).share().block();
		round01 = tacticalService.setInitiative(r01Id, taMelee01.getId(), 11).share().block();
		round01 = tacticalService.setInitiative(r01Id, taMelee02.getId(), 10).share().block();
		round01 = tacticalService.startExecutionPhase(r01Id).share().block();

		MeleeAttackExecution meleeAttackExecution = MeleeAttackExecution.builder()
			.targets(Collections.singletonMap(AttackTargetType.MAIN_HAND, taMelee02.getId()))
			.rolls(Collections.singletonMap(AttackTargetType.MAIN_HAND, OpenRoll.of(56)))
			.build();

		TacticalAction taResolved01 = tacticalActionService.execute(a01.getId(), meleeAttackExecution).share().block();
		assertTrue(taResolved01 instanceof TacticalActionMeleeAttack);
		TacticalActionMeleeAttack meleeResolved01 = (TacticalActionMeleeAttack) taResolved01;

		Map<OffensiveBonusModifier, Integer> mainHandMap = meleeResolved01.getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND);

		assertEquals(-5, mainHandMap.get(OffensiveBonusModifier.PARRY_ATTACK));
		assertEquals(-25, mainHandMap.get(OffensiveBonusModifier.PARRY_DEFENSE));
		assertEquals(20, mainHandMap.get(OffensiveBonusModifier.SHIELD));
	}

}
