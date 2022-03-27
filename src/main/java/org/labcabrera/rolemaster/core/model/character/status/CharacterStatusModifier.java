package org.labcabrera.rolemaster.core.model.character.status;

public interface CharacterStatusModifier {

	default Integer getHp() {
		return 0;
	}

	default Integer getStunned() {
		return 0;
	}

	default Integer getUnconscious() {
		return 0;
	}

}
