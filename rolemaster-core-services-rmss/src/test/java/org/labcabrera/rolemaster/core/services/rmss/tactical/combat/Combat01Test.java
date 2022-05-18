package org.labcabrera.rolemaster.core.services.rmss.tactical.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.dto.tactical.InitiativeDeclaration;
import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterInitiativeDeclaration;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.services.rmss.tactical.AbstractTacticalTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Combat01Test extends AbstractTacticalTest {

	protected TacticalCharacter witchKing;
	protected TacticalCharacter human01;
	protected TacticalCharacter human02;

	@BeforeEach
	void cleanUp() {
		clearData();
		prepare();
	}

	@Test
	void test() {
		witchKing = tacticalService.addNpc(ts.getId(), "the-witch-king").share().block();
		human01 = tacticalService.addNpc(ts.getId(), "human-fighter-i", NpcCustomization.builder()
			.name("human-01").build())
			.share().block();
		human02 = tacticalService.addNpc(ts.getId(), "human-fighter-i", NpcCustomization.builder()
			.name("human-02").build())
			.share().block();

		TacticalRound round01 = tacticalService.startRound(ts.getId()).share().block();

		Map<AttackTargetType, String> targets = new LinkedHashMap<>();
		targets.put(AttackTargetType.MAIN_HAND, human01.getId());
		targets.put(AttackTargetType.OFF_HAND, human02.getId());
		TacticalAction a01 = tacticalActionService.delare(TacticalActionMeleeAttackDeclaration.builder()
			.source(witchKing.getId())
			.roundId(round01.getId())
			.priority(TacticalActionPhase.NORMAL)
			.actionPercent(80)
			.targets(targets)
			.parry(0)
			.meleeAttackType(MeleeAttackType.FULL)
			.meleeAttackMode(MeleeAttackMode.TWO_WEAPONS)
			.build()).share().block();

		InitiativeDeclaration initiativeDeclaration = InitiativeDeclaration.builder().build();
		initiativeDeclaration.getCharacters().add(TacticalCharacterInitiativeDeclaration.builder()
			.characterId(witchKing.getId())
			.initiativeRoll(11)
			.build());
		round01 = tacticalInitiativeService.initiativeDeclaration(auth, ts.getId(), initiativeDeclaration).share().block();

		Map<AttackTargetType, OpenRoll> rolls = new LinkedHashMap<>();
		rolls.put(AttackTargetType.MAIN_HAND, OpenRoll.of(50));
		rolls.put(AttackTargetType.OFF_HAND, OpenRoll.of(40));
		MeleeAttackExecution meleeAttackExecution = MeleeAttackExecution.builder()
			.rolls(rolls)
			.build();

		TacticalActionMeleeAttack attackResolved = (TacticalActionMeleeAttack) tacticalActionService
			.execute(auth, a01.getId(), meleeAttackExecution).share().block();

		assertEquals(TacticalActionState.PENDING_CRITICAL_RESOLUTION, attackResolved.getState());

		Map<String, Integer> criticalRolls = new LinkedHashMap<>();
		criticalRolls.put("roll-01", 11);
		criticalRolls.put("roll-02", 16);
		AttackCriticalExecution criticalExecution = AttackCriticalExecution.builder()
			.rolls(criticalRolls)
			.build();

		TacticalActionMeleeAttack criticalResolved = (TacticalActionMeleeAttack) tacticalActionService
			.executeCritical(attackResolved.getId(), criticalExecution).share().block();

		assertEquals(TacticalActionState.RESOLVED, criticalResolved.getState());

		human01 = this.tacticalCharacterRepository.findById(human01.getId()).share().block();

		assertEquals(2, human01.getCombatStatus().getDebuffs().get(Debuff.STUNNED));
	}

}
