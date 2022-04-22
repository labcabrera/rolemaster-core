package org.labcabrera.rolemaster.core.tactical.combat;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.dto.StrategicSessionCreation;
import org.labcabrera.rolemaster.core.dto.TacticalSessionCreation;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;

public abstract class AbstractBasicCombatTest extends AbstractCombatTest {

	protected StrategicSession sts;

	protected TacticalSession ts;

	protected TacticalCharacter taMelee01;

	protected TacticalCharacter taMelee02;

	protected TacticalCharacter taMelee03;

	protected TacticalCharacter taRanged01;

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

		taMelee01 = tacticalService.addNpc(ts.getId(), "orc-fighter-scimitar-ii", NpcCustomization.builder()
			.name("orc-01").build())
			.share().block();

		taMelee02 = tacticalService.addNpc(ts.getId(), "orc-fighter-scimitar-ii", NpcCustomization.builder()
			.name("orc-02").build())
			.share().block();

		taMelee03 = tacticalService.addNpc(ts.getId(), "orc-fighter-scimitar-ii", NpcCustomization.builder()
			.name("orc-03").build())
			.share().block();

		taRanged01 = tacticalService.addNpc(ts.getId(), "orc-ranged-ii", NpcCustomization.builder()
			.name("orc-ranged-01").build())
			.share().block();
	}
}
