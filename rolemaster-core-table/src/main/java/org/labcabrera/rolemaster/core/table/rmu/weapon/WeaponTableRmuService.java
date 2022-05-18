package org.labcabrera.rolemaster.core.table.rmu.weapon;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.labcabrera.rolemaster.core.table.Table3DIntRange;
import org.labcabrera.rolemaster.core.table.loader.CsvTable3DIntRangeLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeaponTableRmuService {

	private final Map<String, Table3DIntRange<String>> map = new LinkedHashMap<>();

	@Autowired
	private CsvTable3DIntRangeLoader tableLoader;

	@PostConstruct
	public void load() {
		load("broadsword");
	}

	private void load(String weaponTableId) {
		String resource = String.format("data/table/rmu/weapon/%s-rmu.csv", weaponTableId);
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
		Table3DIntRange<String> table = tableLoader.load(in, "-", e -> e);
		map.put(weaponTableId, table);
	}

}
