package org.labcabrera.rolemaster.core.tactical;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMeleeAttackDeclaration;
import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionMovementDeclaration;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackType;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.service.strategic.StrategicSessionService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TacticalServiceImplTest {

	@Autowired
	private StrategicSessionService sessionService;

	@Autowired
	private TacticalService tacticalService;

	@Autowired
	private TacticalActionService tacticalActionService;

	@Test
	void simulateSession() {
		StrategicSession session = sessionService.createSession(StrategicSessionCreation.builder()
			.name("Tactical session test " + LocalDateTime.now().toString())
			.build()).share().block();

		TacticalSessionCreation tacticalSessionCreation = TacticalSessionCreation.builder()
			.strategicSessionId(session.getId())
			.name("Tactical session test " + LocalDateTime.now())
			.build();

		TacticalSession tacticalSession = tacticalService.createSession(tacticalSessionCreation).share().block();

		String npcIdentifier = "ork-fighter-melee-ii";

		TacticalCharacter tc1 = tacticalService.addNpc(tacticalSession.getId(), npcIdentifier).share().block();
		TacticalCharacter tc2 = tacticalService.addNpc(tacticalSession.getId(), npcIdentifier).share().block();
		TacticalCharacter tc3 = tacticalService.addNpc(tacticalSession.getId(), npcIdentifier).share().block();

		assertNotNull(tc1);
		assertNotNull(tc2);
		assertNotNull(tc3);

		TacticalRound round = tacticalService.startRound(tacticalSession.getId()).share().block();

		TacticalActionDeclaration actionDeclaration01 = TacticalActionMeleeAttackDeclaration.builder()
			.roundId(round.getId())
			.priority(TacticalActionPhase.NORMAL)
			.meleeAttackType(MeleeAttackType.FULL)
			.source(tc1.getId())
			.target(tc2.getId())
			.actionPercent(80)
			.build();

		TacticalActionDeclaration actionDeclaration02 = TacticalActionMeleeAttackDeclaration.builder()
			.roundId(round.getId())
			.priority(TacticalActionPhase.DELIBERATE)
			.meleeAttackType(MeleeAttackType.PRESS_AND_MELEE)
			.source(tc1.getId())
			.actionPercent(100)
			.build();

		TacticalActionDeclaration actionDeclaration03 = TacticalActionMovementDeclaration.builder()
			.roundId(round.getId())
			.priority(TacticalActionPhase.SNAP)
			.source(tc3.getId())
			.actionPercent(20)
			.build();

		TacticalActionDeclaration actionDeclaration04 = TacticalActionMovementDeclaration.builder()
			.roundId(round.getId())
			.priority(TacticalActionPhase.NORMAL)
			.source(tc3.getId())
			.actionPercent(50)
			.build();

		TacticalAction action01 = tacticalActionService.delare(actionDeclaration01).share().block();
		TacticalAction action02 = tacticalActionService.delare(actionDeclaration02).share().block();
		TacticalAction action03 = tacticalActionService.delare(actionDeclaration03).share().block();
		TacticalAction action04 = tacticalActionService.delare(actionDeclaration04).share().block();

		assertNotNull(action01);
		assertNotNull(action02);
		assertNotNull(action03);
		assertNotNull(action04);

		tacticalService.startInitiativeDeclaration(round.getId());

		tacticalService.setInitiative(round.getId(), tc1.getId(), 11);
		tacticalService.setInitiative(round.getId(), tc2.getId(), 12);
		tacticalService.setInitiative(round.getId(), tc3.getId(), 13);

		tacticalService.startExecutionPhase(round.getId());

		assertThrows(BadRequestException.class, () -> {
			TacticalActionDeclaration invalidSource = TacticalActionMovementDeclaration.builder()
				.roundId(round.getId())
				.priority(TacticalActionPhase.NORMAL)
				.source("invalid source")
				.actionPercent(50)
				.build();
			tacticalActionService.delare(invalidSource).share().block();
		});
	}

}
