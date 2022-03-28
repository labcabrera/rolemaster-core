package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.combat.Penalty;

public interface CharacterStatusModifier {

	/** A positive value indicates the loss of the same amount of life points. */
	default Integer getHp() {
		return 0;
	}

	default Integer getStunnedRounds() {
		return 0;
	}

	default Integer getUnconsciousRounds() {
		return 0;
	}

	default Integer getCanNotParryRounds() {
		return 0;
	}

	default Integer getMustParryRounds() {
		return 0;
	}

	default Penalty getPenalty() {
		return null;
	}

	default Bleeding getBleeding() {
		return null;
	}

}
