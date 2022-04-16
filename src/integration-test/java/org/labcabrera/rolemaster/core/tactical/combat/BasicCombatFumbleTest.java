package org.labcabrera.rolemaster.core.tactical.combat;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackFacing;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.service.strategic.StrategicSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicCombatFumbleTest extends AbstractCombatTest {

	@Autowired
	private StrategicSessionService strategicSessionService;

	@BeforeEach
	void cleanUp() {
		clearData();
	}

	@Test
	void test() {
		StrategicSession sts = strategicSessionService.createSession(StrategicSessionCreation.builder()
			.name("Test strategic session " + LocalDateTime.now())
			.description("Testing")
			.build()).share().block();

		TacticalSession ts = tacticalService.createSession(TacticalSessionCreation.builder()
			.strategicSessionId(sts.getId())
			.name("Test tactical session " + LocalDateTime.now())
			.description("Testing")
			.build()).share().block();

		String tsId = ts.getId();
		String npcId = "ork-fighter-melee-ii";

		TacticalCharacter cc01 = tacticalService.addNpc(tsId, npcId).share().block();
		TacticalCharacter cc02 = tacticalService.addNpc(tsId, npcId).share().block();

		TacticalRound round01 = tacticalService.startRound(tsId).share().block();
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
			.target(cc02.getId())
			.facing(MeleeAttackFacing.NORMAL)
			.roll(OpenRoll.of(4))
			.build();

		TacticalAction taResolved01 = tacticalActionService.execute(a01.getId(), meleeAttackExecution).share().block();
		assertTrue(taResolved01 instanceof TacticalActionMeleeAttack);
		TacticalActionMeleeAttack meleeResolved01 = (TacticalActionMeleeAttack) taResolved01;

		assertNotNull(meleeResolved01);
		assertEquals(TacticalActionState.PENDING_FUMBLE_RESOLUTION, meleeResolved01.getState());

		FumbleExecution fumbleExecution = FumbleExecution.builder()
			.roll(55)
			.build();

		TacticalAction taResolved02 = tacticalActionService.executeFumble(taResolved01.getId(), fumbleExecution).share().block();

		assertNotNull(taResolved02);
	}

}
