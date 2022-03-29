package org.labcabrera.rolemaster.core.service.tactical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import javax.validation.ValidationException;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.SessionCreationRequest;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreationRequest;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleAttack;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMovement;
import org.labcabrera.rolemaster.core.service.strategic.StrategicSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TacticalServiceTest {

	@Autowired
	private StrategicSessionService sessionService;

	@Autowired
	private TacticalService tacticalService;

	@Test
	void simulateSession() {
		StrategicSession session = sessionService.createSession(SessionCreationRequest.builder()
			.name("Tactical session test " + LocalDateTime.now().toString())
			.build()).share().block();

		TacticalSessionCreationRequest tacticalSessionCreation = TacticalSessionCreationRequest.builder()
			.sessionId(session.getId())
			.name("Tactical session test " + LocalDateTime.now())
			.build();

		TacticalSession tacticalSession = tacticalService.createSession(tacticalSessionCreation).share().block();

		String npcIdentifier = "ork-figther-mele-i";

		TacticalCharacterContext tc1 = tacticalService.addNpc(tacticalSession.getId(), npcIdentifier).share().block();
		TacticalCharacterContext tc2 = tacticalService.addNpc(tacticalSession.getId(), npcIdentifier).share().block();
		TacticalCharacterContext tc3 = tacticalService.addNpc(tacticalSession.getId(), npcIdentifier).share().block();

		assertNotNull(tc1);
		assertNotNull(tc2);
		assertNotNull(tc3);

		runActionDeclarationValidators(tacticalService, tacticalSession);

		TacticalAction action01 = TacticalActionMeleAttack.builder()
			.priority(TacticalActionPhase.NORMAL)
			.meleAttackType(MeleeAttackType.FULL)
			.source(tc1.getNpcInstanceId())
			.target(tc2.getNpcInstanceId())
			.actionPercent(80)
			.build();

		TacticalAction action02 = TacticalActionMeleAttack.builder()
			.priority(TacticalActionPhase.DELIBERATE)
			.meleAttackType(MeleeAttackType.PRESS_AND_MELEE)
			.source(tc1.getNpcInstanceId())
			.actionPercent(100)
			.build();

		TacticalAction action03 = TacticalActionMovement.builder()
			.priority(TacticalActionPhase.SNAP)
			.source(tc3.getNpcInstanceId())
			.actionPercent(20)
			.build();

		TacticalAction action04 = TacticalActionMovement.builder()
			.priority(TacticalActionPhase.NORMAL)
			.source(tc3.getNpcInstanceId())
			.actionPercent(50)
			.build();

		TacticalRound round = tacticalService.startRound(tacticalSession.getId()).share().block();

		round = tacticalService.declare(tacticalSession.getId(), action01).share().block();
		round = tacticalService.declare(tacticalSession.getId(), action02).share().block();
		round = tacticalService.declare(tacticalSession.getId(), action03).share().block();
		round = tacticalService.declare(tacticalSession.getId(), action04).share().block();

		round = tacticalService.getCurrentRound(tacticalSession.getId()).share().block();

		assertEquals(1, round.getRound());

		assertEquals(4, round.getActions().size());

		tacticalService.setInitiative(round.getId(), "character-01", 7);
		tacticalService.setInitiative(round.getId(), "character-02", 16);
		tacticalService.setInitiative(round.getId(), "character-03", 11);
	}

	private void runActionDeclarationValidators(TacticalService tacticalService, TacticalSession tacticalSession) {
		assertThrows(ValidationException.class, () -> {
			TacticalAction action = TacticalActionMeleAttack.builder()
				.priority(TacticalActionPhase.NORMAL)
				.meleAttackType(MeleeAttackType.FULL)
				.source("s1")
				.target("t1")
				.actionPercent(100)
				.build();
			tacticalService.declare(null, action).share().block();
		});
		assertThrows(ValidationException.class, () -> {
			TacticalAction action = TacticalActionMovement.builder().build();
			tacticalService.declare(tacticalSession.getId(), action).share().block();
		});
		assertThrows(ValidationException.class, () -> {
			TacticalAction invalidActionMissingTarget = TacticalActionMeleAttack.builder()
				.source("s1")
				.priority(TacticalActionPhase.NORMAL)
				.meleAttackType(MeleeAttackType.FULL)
				.actionPercent(100)
				.build();
			tacticalService.declare(tacticalSession.getId(), invalidActionMissingTarget).share().block();
		});
		assertThrows(ValidationException.class, () -> {
			TacticalAction invalidSnapActionPercent = TacticalActionMovement.builder()
				.priority(TacticalActionPhase.SNAP)
				.source("s1")
				.actionPercent(21)
				.build();
			tacticalService.declare(tacticalSession.getId(), invalidSnapActionPercent).share().block();
		});
		assertThrows(ValidationException.class, () -> {
			TacticalAction invalidNormalActionPercent = TacticalActionMovement.builder()
				.priority(TacticalActionPhase.NORMAL)
				.source("s1")
				.actionPercent(81)
				.build();
			tacticalService.declare(tacticalSession.getId(), invalidNormalActionPercent).share().block();
		});
	}

}
