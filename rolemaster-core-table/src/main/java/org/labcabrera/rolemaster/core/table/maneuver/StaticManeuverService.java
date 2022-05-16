package org.labcabrera.rolemaster.core.table.maneuver;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StaticManeuverService {

	@Autowired
	private ObjectMapper objectMapper;

	private StaticManeuverTable staticManeuverTable;

	public StaticManeuverResult getResult(int roll) {
		return staticManeuverTable.getResult(roll);
	}

	@PostConstruct
	private void readTable() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/table/maneuver/static-maneuver.json")) {
			this.staticManeuverTable = objectMapper.readerFor(StaticManeuverTable.class).readValue(in);
		}
	}
}
