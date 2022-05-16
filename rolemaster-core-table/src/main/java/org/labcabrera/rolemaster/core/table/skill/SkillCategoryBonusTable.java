package org.labcabrera.rolemaster.core.table.skill;

import java.util.function.IntUnaryOperator;

import org.springframework.stereotype.Component;

@Component
class SkillCategoryBonusTable implements IntUnaryOperator {

	@Override
	public int applyAsInt(int value) {
		if (value == 0) {
			return -15;
		}
		else if (value < 11) {
			return value * 2;
		}
		else if (value < 21) {
			return value + 10;
		}
		else if (value == 21 || value == 22) {
			return 31;
		}
		else if (value == 23 || value == 24) {
			return 32;
		}
		else if (value == 25 || value == 26) {
			return 33;
		}
		else if (value == 27 || value == 28) {
			return 34;
		}
		return 35;
	}

}
