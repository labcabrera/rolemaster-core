package org.labcabrera.rolemaster.core.table.maneuver;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MovingManeuverService {

	@Autowired
	private ObjectMapper objectMapper;

	private MovingManeuverTable movingManeuverTable;

	public MovingManeuverResult getResult(ManeuverDificulty dificulty, int roll) {
		return movingManeuverTable.getResult(dificulty, roll);
	}

	@PostConstruct
	private void readTable() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/table/maneuver/moving-maneuver.json")) {
			this.movingManeuverTable = objectMapper.readerFor(MovingManeuverTable.class).readValue(in);
		}
	}
}
