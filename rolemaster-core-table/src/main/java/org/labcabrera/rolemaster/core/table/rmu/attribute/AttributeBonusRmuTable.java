package org.labcabrera.rolemaster.core.table.rmu.attribute;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.labcabrera.rolemaster.core.table.Table2DIntRange;
import org.labcabrera.rolemaster.core.table.loader.CsvTable2DIntRangeLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttributeBonusRmuTable {

	private static final String RESOURCE = "data/table/rmu/attribute/attribute-bonus-rmu.csv";

	private Table2DIntRange<Integer> table;

	@Autowired
	private CsvTable2DIntRangeLoader tableLoader;

	@PostConstruct
	public void load() {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCE);
		table = tableLoader.load(in, "-", Integer::parseInt);
	}

	public Integer getBonus(Integer value) {
		return table.apply(value);
	}

}
