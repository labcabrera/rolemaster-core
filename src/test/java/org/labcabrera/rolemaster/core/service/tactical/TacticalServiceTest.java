package org.labcabrera.rolemaster.core.service.tactical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.SessionCreationRequest;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreationRequest;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.ActionPriority;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionAttack;
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
	void test() {
		StrategicSession session = sessionService.createSession(SessionCreationRequest.builder()
			.name("Tactical session test " + LocalDateTime.now().toString())
			.build()).share().block();

		TacticalAction action01 = TacticalActionAttack.builder()
			.characterId("character-01")
			.phase(ActionPriority.NORMAL)
			.targetCharacterId("character-02")
			.actionPercent(100)
			.build();

		TacticalAction action02 = TacticalActionAttack.builder()
			.characterId("character-02")
			.phase(ActionPriority.SNAP)
			.targetCharacterId("character-01")
			.actionPercent(100)
			.build();

		TacticalAction action03 = TacticalActionMovement.builder()
			.characterId("character-03")
			.phase(ActionPriority.NORMAL)
			.build();

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

		TacticalRound round = tacticalService.startRound(tacticalSession.getId()).share().block();

		tacticalService.declare(tacticalSession.getId(), action01).share().block();
		tacticalService.declare(tacticalSession.getId(), action02).share().block();
		tacticalService.declare(tacticalSession.getId(), action03).share().block();

		round = tacticalService.getCurrentRound(tacticalSession.getId()).share().block();

		assertEquals(1, round.getRound());

		tacticalService.setInitiative(round.getId(), "character-01", 7);
		tacticalService.setInitiative(round.getId(), "character-02", 16);
		tacticalService.setInitiative(round.getId(), "character-03", 11);

	}

}
