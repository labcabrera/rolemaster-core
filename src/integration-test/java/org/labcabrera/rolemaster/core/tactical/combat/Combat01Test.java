package org.labcabrera.rolemaster.core.tactical.combat;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
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

	@Autowired
	private StrategicSessionRepository strategicSessionRepository;

	@Autowired
	private TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	private TacticalRoundRepository roundRepository;

	@Autowired
	private TacticalActionRepository tacticalActionRepository;

	@Autowired
	private TacticalCharacterContextRepository tacticalCharacterRepository;

	@BeforeEach
	void cleanUp() {
		strategicSessionRepository.deleteAll()
			.then(tacticalSessionRepository.deleteAll())
			.then(roundRepository.deleteAll())
			.then(tacticalActionRepository.deleteAll())
			.then(tacticalCharacterRepository.deleteAll())
			.share().block();
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
		String npcId01 = "ork-fighter-melee-i";
		String npcId02 = "ork-fighter-range-i";

		TacticalCharacter cc01 = tacticalService.addNpc(tsId, npcId01).share().block();
		TacticalCharacter cc02 = tacticalService.addNpc(tsId, npcId02).share().block();

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

		round01 = tacticalService.startInitiativeDeclaration(r01Id).share().block();

		round01 = tacticalService.setInitiative(r01Id, cc01.getId(), 11).share().block();

		round01 = tacticalService.startExecutionPhase(r01Id).share().block();

		List<TacticalAction> actionQueue = tacticalService.getActionQueue(r01Id).share().collectList().share().block();
		assertEquals(1, actionQueue.size());

		MeleeAttackExecution meleeAttackExecution = MeleeAttackExecution.builder()
			.actionId(a01.getId())
			.target(cc02.getId())
			.roll(OpenRoll.builder()
				.result(56)
				.rolls(Arrays.asList(56))
				.build())
			.build();

		tacticalActionService.execute(meleeAttackExecution).share().block();

		Assertions.assertNotNull(round01);

	}

}
