package org.labcabrera.rolemaster.core.table.rmss.maneuver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
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
		for (ManeuverDifficulty md : ManeuverDifficulty.values()) {
			if (md != ManeuverDifficulty.NONE) {
				for (int i = -201; i < 280; i++) {
					assertTrue(table.checkConsistence(md, i), "Invalid consistence: " + md + " -> " + i);
					assertNotNull(table.getResult(md, i).getResult(), "Invalid consistence: " + md + " -> " + i);
				}
			}
		}
		assertNotNull(table);
	}

	private MovingManeuverTable readTable() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/table/rmss/maneuver/moving-maneuver.json")) {
			return objectMapper.readerFor(MovingManeuverTable.class).readValue(in);
		}
	}

}
