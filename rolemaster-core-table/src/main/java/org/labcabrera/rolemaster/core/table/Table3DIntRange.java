package org.labcabrera.rolemaster.core.table;

import java.util.Map;
import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.model.Range;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;

public class Table3DIntRange<E> implements Table3D<Integer, Integer, E> {

	private final Map<Range<Integer>, Map<Integer, E>> map;

	public Table3DIntRange(Map<Range<Integer>, Map<Integer, E>> map) {
		this.map = map;
	}

	@Override
	public E apply(Integer i0, Integer i1) {
		for (Entry<Range<Integer>, Map<Integer, E>> entry : map.entrySet()) {
			if (checkKeyRange(entry.getKey(), i0)) {
				return entry.getValue().get(i1);
			}
		}
		throw new DataConsistenceException("Invalid entry.");
	}

	private boolean checkKeyRange(Range<Integer> range, Integer roll) {
		boolean checkMin = range.getMin() == null || roll >= range.getMin();
		boolean checkMax = range.getMax() == null || roll <= range.getMax();
		return checkMin && checkMax;
	}

}
