package org.labcabrera.rolemaster.core.table.skill;

import java.util.List;

public class SkillSpecialBonusTable {

	/**
	 * @param value
	 * @param mask Example [6,5,4,3]
	 * @return
	 */
	public Integer apply(Integer value, List<Integer> mask) {
		if (value < 11) {
			return value * mask.get(0);
		}
		else if (value < 21) {
			int tmp = mask.get(0) * 10;
			return tmp + (value - 10) * mask.get(1);
		}
		else if (value < 31) {
			int tmp = mask.get(0) * 10 + mask.get(1) * 10;
			return tmp + (value - 20) * mask.get(2);
		}
		int tmp = mask.get(0) * 10 + mask.get(1) * 10 + mask.get(2) * 10;
		return tmp + (value - 30) * 3;
	}

}
