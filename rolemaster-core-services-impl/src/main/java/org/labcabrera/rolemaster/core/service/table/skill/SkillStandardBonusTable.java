package org.labcabrera.rolemaster.core.service.table.skill;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.IntUnaryOperator;

import org.springframework.stereotype.Component;

@Component
class SkillStandardBonusTable implements IntUnaryOperator {

	@Override
	public int applyAsInt(int value) {
		if (value == 0) {
			return -15;
		}
		else if (value < 11) {
			return value * 3;
		}
		else if (value < 21) {
			return 30 + (value - 10) * 2;
		}
		else if (value < 31) {
			return 50 + (value - 20);
		}
		BigDecimal bd = new BigDecimal(value - 30).divide(new BigDecimal(2), 0, RoundingMode.HALF_UP);
		return 60 + bd.intValue();
	}

}
