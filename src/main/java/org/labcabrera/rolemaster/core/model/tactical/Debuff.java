package org.labcabrera.rolemaster.core.model.tactical;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Debuff {

	/**
	 * While stunned, a combatant may not attack and may only parry with half of his
	 * offensive bonus. The only other allow- able actions are movement and maneuvers
	 * (modified by at least -50). In addition to normal modifications, stunned maneuvers
	 * are also modified by three times the character’s SD stat bonus.
	 */
	STUNNED("stunned"),

	// Caido
	DOWNED("downed"),

	// Derribado
	PRONE("prone"),

	/**
	 * If a character is surprised, a GM may limit his activity for his first round of
	 * reaction. We suggest a GM allow a surprised character to take only one deliberate
	 * action. The actual % activity allowed should range from 0-100% based upon an
	 * orientation roll (see Section 18.4, p. 53).
	 */
	SURPRISED("surprised"),

	UNCONSCIOUS("unconscious"),

	CANT_PARRY("cant-parry"),

	MUST_PARRY("must-parry"),

	SHOCK("shock");

	String name;

	private Debuff(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
