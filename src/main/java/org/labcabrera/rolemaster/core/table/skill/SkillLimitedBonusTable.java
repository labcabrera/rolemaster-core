package org.labcabrera.rolemaster.core.table.skill;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Component;

/**
 * The limited skill rank bonus progression is given in Table T-2.2. The bonus is 0 if the
 * rank is zero and +1 if the rank is one. The bonus increases by 1 for each of ranks two
 * to twenty, by 0.5 for each of ranks twenty-one to thirty, and by 0 for each rank above
 * thirty.
 */
@Component
class SkillLimitedBonusTable implements UnaryOperator<Integer> {

	@Override
	public Integer apply(Integer value) {
		if (value < 21) {
			return value;
		}
		else if (value < 31) {
			BigDecimal bd = new BigDecimal(value - 20).multiply(new BigDecimal("0.5"));
			BigDecimal rounded = bd.setScale(0, RoundingMode.HALF_UP);
			return 20 + rounded.intValue();
		}
		return 25;
	}

}
