package org.labcabrera.rolemaster.core.tactical.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.WeaponBreakageExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemFeatureType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackFacing;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterItemResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicCombatBreakageSuccessTest extends AbstractBasicCombatTest {

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

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
			.priority(TacticalActionPhase.DELIBERATE)
			.actionPercent(100)
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
			.rolls(Collections.singletonMap(AttackTargetType.MAIN_HAND, OpenRoll.of(44)))
			.facingMap(Collections.singletonMap(AttackTargetType.MAIN_HAND, MeleeAttackFacing.FLANK))
			.build();

		TacticalActionMeleeAttack resolved = (TacticalActionMeleeAttack) tacticalActionService.execute(a01.getId(), meleeAttackExecution)
			.share().block();

		assertEquals(TacticalActionState.PENDING_BREAKAGE_RESOLUTION, resolved.getState());

		WeaponBreakageExecution breakageExecution = WeaponBreakageExecution.builder()
			.rolls(Collections.singletonMap(AttackTargetType.MAIN_HAND, 50))
			.build();

		resolved = (TacticalActionMeleeAttack) tacticalActionService.executeBreakage(a01.getId(), breakageExecution).share().block();

		assertEquals(TacticalActionState.RESOLVED, resolved.getState());

		assertEquals(7, resolved.getAttackResults().get(AttackTargetType.MAIN_HAND).getHp());
		assertEquals(79, resolved.getAttackResults().get(AttackTargetType.MAIN_HAND).getResult());
		assertEquals(35, resolved.getAttackResults().get(AttackTargetType.MAIN_HAND).getTotalBonus());

		assertEquals(40, resolved.getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).get(OffensiveBonusModifier.SKILL));
		assertEquals(-30, resolved.getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).get(OffensiveBonusModifier.DEFENSIVE_BONUS));
		assertEquals(15, resolved.getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND).get(OffensiveBonusModifier.MELEE_FACING));

		taMelee01 = tacticalCharacterRepository.findById(taMelee01.getId()).share().block();
		CharacterItem item = itemResolver.getMainHandWeapon(taMelee01);
		assertFalse(item.hasFeature(ItemFeatureType.BROKEN));
	}

}
