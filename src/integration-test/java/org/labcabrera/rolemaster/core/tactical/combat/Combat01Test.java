package org.labcabrera.rolemaster.core.tactical.combat;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackType;
import org.labcabrera.rolemaster.core.service.strategic.StrategicSessionService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Combat01Test {

	@Autowired
	private StrategicSessionService strategicSessionService;

	@Autowired
	private TacticalService tacticalService;

	@Autowired
	private TacticalActionService tacticalActionService;

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
		String npcId01 = "ork-fighter-melee-i";
		String npcId02 = "ork-fighter-range-i";

		TacticalCharacterContext cc01 = tacticalService.addNpc(tsId, npcId01).share().block();
		TacticalCharacterContext cc02 = tacticalService.addNpc(tsId, npcId02).share().block();

		TacticalRound round01 = tacticalActionService.delare(tsId, TacticalActionMeleeAttackDeclaration.builder()
			.priority(TacticalActionPhase.NORMAL)
			.actionPercent(80)
			.source(cc01.getId())
			.parry(0)
			.meleeAttackType(MeleeAttackType.PRESS_AND_MELEE)
			.build()).share().block();

		tacticalActionService.setInitiative(tsId, cc01.getId(), 10);

		tacticalActionService.startExecutionPhase(tsId);

		MeleeAttackExecution meleeAttackExecution = MeleeAttackExecution.builder()
			.target(cc02.getId())
			.openRoll(OpenRoll.builder()
				.result(56)
				.rolls(Arrays.asList(56))
				.build())
			.build();

		tacticalActionService.execute(tsId, cc01.getId(), TacticalActionPhase.NORMAL, meleeAttackExecution).share()
			.block();

		Assertions.assertNotNull(round01);

	}

}
