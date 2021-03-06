package org.labcabrera.rolemaster.core.table.rmss.skill;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.IntUnaryOperator;

import org.springframework.stereotype.Component;

@Component
class SkillCombinedBonusTable implements IntUnaryOperator {

	@Override
	public int applyAsInt(int value) {
		if (value == 0) {
			return -15;
		}
		else if (value < 11) {
			return value * 5;
		}
		else if (value < 21) {
			return 50 + (value - 10) * 3;
		}
		else if (value < 31) {
			BigDecimal bd = new BigDecimal(value - 20).multiply(new BigDecimal("1.5"));
			BigDecimal rounded = bd.setScale(0, RoundingMode.HALF_UP);
			return 80 + rounded.intValue();
		}
		BigDecimal bd = new BigDecimal(value - 30).multiply(new BigDecimal("0.5"));
		BigDecimal rounded = bd.setScale(0, RoundingMode.HALF_UP);
		return 95 + rounded.intValue();
	}

}
