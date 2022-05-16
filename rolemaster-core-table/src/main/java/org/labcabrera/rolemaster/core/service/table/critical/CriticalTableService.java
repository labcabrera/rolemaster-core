package org.labcabrera.rolemaster.core.service.table.critical;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CriticalTableService {

	@Autowired
	private ObjectMapper objectMapper;

	private Map<CriticalType, CriticalTable> tables = new EnumMap<>(CriticalType.class);

	@PostConstruct
	public void loadTables() throws IOException {
		tables.put(CriticalType.S, loadTable("data/table/critical/critical-table-s.json"));
		tables.put(CriticalType.P, loadTable("data/table/critical/critical-table-p.json"));
		tables.put(CriticalType.K, loadTable("data/table/critical/critical-table-k.json"));
		tables.put(CriticalType.G, loadTable("data/table/critical/critical-table-g.json"));
		tables.put(CriticalType.HEAT, loadTable("data/table/critical/critical-table-heat.json"));
	}

	public CriticalTableResult getResult(CriticalType type, CriticalSeverity severity, Integer roll) {
		if (!tables.containsKey(type)) {
			throw new NotImplementedException("Not implemented critical type " + type);
		}
		return tables.get(type).get(severity, roll);
	}

	private CriticalTable loadTable(String resource) throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
			return objectMapper.readerFor(CriticalTable.class).readValue(in);
		}
	}

}
