package org.labcabrera.rolemaster.core.model.tactical.actions;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum MeleeAttackType {

	FULL("full", "The attacker’s OB receives a +10 modification, but the target of the attack must be declared during "
		+ "the Action Declaration Phase. Any movement must be declared as a separate action."),

	PRESS_AND_MELEE("pressAndMelee", "The target of the attack must be declared during the Action Declaration Phase and the target "
		+ "must be adjacent at that time. If the target attempts to move away before the attack is resolved, the attacker may "
		+ "attempt to move after him. Such movement only results in half the normal OB modification for less than 100% activity "
		+ "used to attack."),

	REACT_AND_MELEE("reactAndMelee", "The attacker’s OB receives a -10 modification, but the target of the attack need not be "
		+ "declared during the Action Declaration Phase. As an action in any of the three phases (snap, normal, or deliberate), "
		+ "the attacker can attempt to move to and attack anyone within 50'. If he has not done so by the end of the round, "
		+ "he may move up to 50% of his normal movement. Apply the normal OB modifications for less than 100% activity "
		+ "used to attack.");

	private String name;

	@Getter
	private String description;

	private MeleeAttackType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
