package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import org.springframework.stereotype.Component;

@Component
public class MeleeAttackOffensiveBonusCalculator {

	public Integer calculate() {
		int result = 0;

		result += getSkillBonus();

		return result;
	}

	private Integer getSkillBonus() {
		return 0;
	}

}
