package org.labcabrera.rolemaster.core.tactical.combat;

import org.labcabrera.rolemaster.core.dto.NpcCustomization;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.tactical.AbstractTacticalTest;

public abstract class AbstractBasicCombatTest extends AbstractTacticalTest {

	protected StrategicSession sts;

	protected TacticalSession ts;

	protected TacticalCharacter taMelee01;

	protected TacticalCharacter taMelee02;

	protected TacticalCharacter taMelee03;

	protected TacticalCharacter taRanged01;

	protected void prepare() {
		super.prepare();

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
