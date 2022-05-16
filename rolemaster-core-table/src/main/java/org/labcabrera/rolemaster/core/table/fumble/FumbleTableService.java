package org.labcabrera.rolemaster.core.table.fumble;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.labcabrera.rolemaster.core.model.combat.FumbleTableResult;
import org.labcabrera.rolemaster.core.model.tactical.action.FumbleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FumbleTableService {

	@Autowired
	private ObjectMapper objectMapper;

	private FumbleTable fumbleTable;

	@PostConstruct
	public void loadTables() throws IOException {
		try (
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/table/critical/fumble-table.json")) {
			fumbleTable = objectMapper.readerFor(FumbleTable.class).readValue(in);
		}
	}

	public FumbleTableResult getResult(FumbleType type, Integer roll) {
		return fumbleTable.get(type, roll);
	}
}
