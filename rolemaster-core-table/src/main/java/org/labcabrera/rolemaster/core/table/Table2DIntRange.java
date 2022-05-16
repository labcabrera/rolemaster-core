package org.labcabrera.rolemaster.core.table;

import java.util.Map;
import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.model.Range;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;

public class Table2DIntRange<E> extends RangeTable implements Table2D<Integer, E> {

	private final Map<Range<Integer>, E> map;

	public Table2DIntRange(Map<Range<Integer>, E> map) {
		this.map = map;
	}

	@Override
	public E apply(Integer value) {
		for (Entry<Range<Integer>, E> entry : map.entrySet()) {
			if (checkKeyRange(entry.getKey(), value)) {
				return entry.getValue();
			}
		}
		throw new DataConsistenceException("Invalid entry for value " + value + ".");
	}

}
