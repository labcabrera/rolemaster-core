package org.labcabrera.rolemaster.core.model.tactical.action;

import org.labcabrera.rolemaster.core.model.CodeNameEnum;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum MeleeAttackType implements CodeNameEnum {

	FULL("full", "Full", "The attacker’s OB receives a +10 modification, but the target of the attack must be declared during "
		+ "the Action Declaration Phase. Any movement must be declared as a separate action."),

	PRESS_AND_MELEE("pressAndMelee", "Press and melee",
		"The target of the attack must be declared during the Action Declaration Phase and the target "
			+ "must be adjacent at that time. If the target attempts to move away before the attack is resolved, the attacker may "
			+ "attempt to move after him. Such movement only results in half the normal OB modification for less than 100% activity "
			+ "used to attack."),

	REACT_AND_MELEE("reactAndMelee", "React and melee",
		"The attacker’s OB receives a -10 modification, but the target of the attack need not be "
			+ "declared during the Action Declaration Phase. As an action in any of the three phases (snap, normal, or deliberate), "
			+ "the attacker can attempt to move to and attack anyone within 50'. If he has not done so by the end of the round, "
			+ "he may move up to 50% of his normal movement. Apply the normal OB modifications for less than 100% activity "
			+ "used to attack.");

	private String code;

	@Getter
	private String name;

	@Getter
	private String detail;

	private MeleeAttackType(String code, String name, String detail) {
		this.code = code;
		this.name = name;
		this.detail = detail;
	}

	@JsonValue
	public String getCode() {
		return code;
	}

}
