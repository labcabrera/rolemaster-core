package org.labcabrera.rolemaster.core.table.rmss.maneuver;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
import org.labcabrera.rolemaster.core.model.maneuver.MovingManeuverResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MovingManeuverServiceImpl {

	@Autowired
	private ObjectMapper objectMapper;

	private MovingManeuverTable movingManeuverTable;

	public MovingManeuverResult getResult(ManeuverDifficulty difficulty, int roll) {
		return movingManeuverTable.getResult(difficulty, roll);
	}

	@PostConstruct
	private void readTable() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/table/rmss/maneuver/moving-maneuver.json")) {
			this.movingManeuverTable = objectMapper.readerFor(MovingManeuverTable.class).readValue(in);
		}
	}
}
