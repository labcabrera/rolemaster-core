package org.labcabrera.rolemaster.core.table;

import org.labcabrera.rolemaster.core.model.Range;

public abstract class RangeTable {

	protected boolean checkKeyRange(Range<Integer> range, Integer roll) {
		boolean checkMin = range.getMin() == null || roll >= range.getMin();
		boolean checkMax = range.getMax() == null || roll <= range.getMax();
		return checkMin && checkMax;
	}
}
