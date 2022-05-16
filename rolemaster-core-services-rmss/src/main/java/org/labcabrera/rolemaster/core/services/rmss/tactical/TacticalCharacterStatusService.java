package org.labcabrera.rolemaster.core.services.rmss.tactical;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.springframework.stereotype.Service;

@Service
public class TacticalCharacterStatusService {

	private static final List<Debuff> CANT_PARRY_DEBUFS = Arrays.asList(Debuff.CANT_PARRY, Debuff.SHOCK, Debuff.PRONE, Debuff.UNCONSCIOUS);

	public boolean canParry(TacticalCharacter tc) {
		for (Debuff debuff : CANT_PARRY_DEBUFS) {
			if (tc.getCombatStatus().getDebuffs().containsKey(debuff)) {
				return false;
			}
		}
		return true;
	}

	public boolean canBlock() {
		//TODO
		return true;
	}

}
