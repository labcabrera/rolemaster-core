package org.labcabrera.rolemaster.core.table.maneuver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class MovingManeuverTableTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test() throws IOException {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		MovingManeuverTable table = readTable();
		for (ManeuverDificulty md : ManeuverDificulty.values()) {
			if (md != ManeuverDificulty.NONE) {
				for (int i = -201; i < 280; i++) {
					assertTrue(table.checkConsistence(md, i), "Invalid consistence: " + md + " -> " + i);
					assertTrue(table.getResult(md, i).getResult() != null, "Invalid consistence: " + md + " -> " + i);
				}
			}
		}
		assertNotNull(table);
	}

	private MovingManeuverTable readTable() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/populator/maneuvers/moving-maneuver.json")) {
			return objectMapper.readerFor(MovingManeuverTable.class).readValue(in);
		}
	}

}
