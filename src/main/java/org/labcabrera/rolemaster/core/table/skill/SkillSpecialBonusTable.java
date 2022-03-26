package org.labcabrera.rolemaster.core.table.skill;

import java.util.function.UnaryOperator;

public class SkillSpecialBonusTable implements UnaryOperator<Integer> {

	@Override
	public Integer apply(Integer value) {
		if (value < 11) {
			return value * 6;
		}
		else if (value < 21) {
			return 60 + (value - 10) * 5;
		}
		else if (value < 31) {
			return 110 + (value - 20) * 4;
		}
		return 150 + (value - 30) * 3;
	}

}
