package org.labcabrera.rolemaster.core.tactical.combat;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;

public abstract class AbstractBasicCombatTest extends AbstractCombatTest {

	protected StrategicSession sts;

	protected TacticalSession ts;

	protected TacticalCharacter cc01;

	protected TacticalCharacter cc02;

	protected void prepare() {
		sts = strategicSessionService.createSession(StrategicSessionCreation.builder()
			.name("Test strategic session " + LocalDateTime.now())
			.description("Testing")
			.build()).share().block();

		ts = tacticalService.createSession(TacticalSessionCreation.builder()
			.strategicSessionId(sts.getId())
			.name("Test tactical session " + LocalDateTime.now())
			.description("Testing")
			.build()).share().block();

		String npcId = "ork-fighter-melee-ii";

		cc01 = tacticalService.addNpc(ts.getId(), npcId).share().block();
		cc02 = tacticalService.addNpc(ts.getId(), npcId).share().block();
	}
}
