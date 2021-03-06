package org.labcabrera.rolemaster.core.services.rmss.tactical.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.dto.tactical.InitiativeDeclaration;
import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterInitiativeDeclaration;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.OffensiveBonusModifier;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicCombatSpecialAttackTest extends AbstractBasicCombatTest {

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void test() {

		TacticalCharacter taGollum = tacticalService.addNpc(ts.getId(), "gollum").share().block();

		TacticalRound round01 = tacticalService.startRound(ts.getId()).share().block();

		TacticalAction a01 = tacticalActionService.delare(TacticalActionMeleeAttackDeclaration.builder()
			.roundId(round01.getId())
			.priority(TacticalActionPhase.DELIBERATE)
			.actionPercent(100)
			.meleeAttackType(MeleeAttackType.FULL)
			.meleeAttackMode(MeleeAttackMode.SPECIAL_ATTACK)
			.specialAttack("Grapple")
			.source(taGollum.getId())
			.targets(Collections.singletonMap(AttackTargetType.MAIN_HAND, taMelee01.getId()))
			.build()).share().block();

		InitiativeDeclaration initiativeDeclaration = InitiativeDeclaration.builder().build();
		initiativeDeclaration.getCharacters().add(TacticalCharacterInitiativeDeclaration.builder()
			.characterId(taGollum.getId())
			.initiativeRoll(11)
			.build());
		round01 = tacticalInitiativeService.initiativeDeclaration(auth, ts.getId(), initiativeDeclaration).share().block();

		MeleeAttackExecution meleeAttackExecution = MeleeAttackExecution.builder()
			.rolls(Collections.singletonMap(AttackTargetType.MAIN_HAND, OpenRoll.of(85)))
			.build();

		TacticalActionMeleeAttack meleeAttack = (TacticalActionMeleeAttack) tacticalActionService.execute(auth,a01.getId(), meleeAttackExecution)
			.share().block();

		assertNotNull(meleeAttack);

		Map<OffensiveBonusModifier, Integer> bonusMap = meleeAttack.getOffensiveBonusMap().get(AttackTargetType.MAIN_HAND);

		assertEquals(125, bonusMap.get(OffensiveBonusModifier.SKILL));
		assertEquals(-30, bonusMap.get(OffensiveBonusModifier.DEFENSIVE_BONUS));
		assertEquals(10, bonusMap.get(OffensiveBonusModifier.PRIORITY));
		assertEquals(10, bonusMap.get(OffensiveBonusModifier.MELEE_TYPE));

		assertEquals(1, meleeAttack.getAttackResults().size());
		assertEquals(7, meleeAttack.getAttackResults().get(AttackTargetType.MAIN_HAND).getHp());
		assertEquals(120, meleeAttack.getAttackResults().get(AttackTargetType.MAIN_HAND).getResult());
		assertEquals(115, meleeAttack.getAttackResults().get(AttackTargetType.MAIN_HAND).getTotalBonus());
		assertEquals("grapple", meleeAttack.getAttackResults().get(AttackTargetType.MAIN_HAND).getWeaponTableId());

		assertEquals(1, meleeAttack.getCriticalResults().size());
		assertEquals(CriticalSeverity.C, meleeAttack.getCriticalResults().get(AttackTargetType.MAIN_HAND).iterator().next().getSeverity());
		assertEquals(CriticalType.G, meleeAttack.getCriticalResults().get(AttackTargetType.MAIN_HAND).iterator().next().getType());
	}

}
