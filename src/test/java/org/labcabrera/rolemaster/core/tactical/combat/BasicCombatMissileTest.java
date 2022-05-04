package org.labcabrera.rolemaster.core.tactical.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMissileAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.combat.Cover;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicCombatMissileTest extends AbstractBasicCombatTest {

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void test() {
		TacticalRound round01 = tacticalService.startRound(ts.getId()).share().block();

		TacticalAction a01 = tacticalActionService.delare(TacticalActionMissileAttackDeclaration.builder()
			.roundId(round01.getId())
			.priority(TacticalActionPhase.NORMAL)
			.actionPercent(80)
			.source(taRanged01.getId())
			.target(taMelee02.getId())
			.build()).share().block();

		assertNotNull(a01);
		assertNotNull(a01.getState());
		assertEquals(TacticalActionState.PENDING, a01.getState());

		round01 = tacticalService.startInitiativeDeclaration(round01.getId()).share().block();

		round01 = tacticalService.setInitiative(round01.getId(), taRanged01.getId(), 15).share().block();

		round01 = tacticalService.startExecutionPhase(round01.getId()).share().block();

		List<TacticalAction> actionQueue = tacticalService.getActionQueue(round01.getId()).share().collectList().share().block();
		assertEquals(1, actionQueue.size());

		MissileAttackExecution missileAttackExecution = MissileAttackExecution.builder()
			.distance(36f)
			.cover(Cover.NONE)
			.preparationRounds(3)
			.roll(OpenRoll.of(110))
			.build();

		TacticalAction actionResolved = tacticalActionService.execute(a01.getId(), missileAttackExecution).share().block();
		assertTrue(actionResolved instanceof TacticalActionAttack);
		TacticalActionAttack missileResolved01 = (TacticalActionAttack) actionResolved;

		assertNotNull(missileResolved01);
		Map<OffensiveBonusModifier, Integer> bonusMap = missileResolved01.getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND);

		assertEquals(40, bonusMap.get(OffensiveBonusModifier.SKILL));
		assertEquals(-30, bonusMap.get(OffensiveBonusModifier.DEFENSIVE_BONUS));
		assertEquals(-40, bonusMap.get(OffensiveBonusModifier.DISTANCE));

		assertEquals(3L, bonusMap.values().stream().filter(e -> e != 0).count());
		assertEquals(-30, bonusMap.values().stream().reduce(0, (a, b) -> a + b));

		assertEquals(TacticalActionState.RESOLVED, missileResolved01.getState());
		assertEquals(5, missileResolved01.getAttackResults().get(AttackTargetType.MAIN_HAND).getHp());
	}

}
