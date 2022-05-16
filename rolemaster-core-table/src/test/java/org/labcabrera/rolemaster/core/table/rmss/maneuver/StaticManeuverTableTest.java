package org.labcabrera.rolemaster.core.table.rmss.maneuver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class StaticManeuverTableTest {

	@Autowired
	private ObjectMapper objectMapper;

	private StaticManeuverTable table;

	@BeforeEach
	void configure() throws IOException {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		table = readTable();
	}

	@Test
	void testConsistency() {
		for (int i = -50; i < 200; i++) {
			assertTrue(table.checkConsistence(i), "Invalid consistence: " + i);
		}
	}

	@Test
	void testFailures() throws IOException {
		StaticManeuverResult result = table.getResult(-26);
		assertEquals("Spectacular Failure", result.getName());
		assertEquals(0, result.getSuccessPercent());

		result = table.getResult(-25);
		assertEquals("Absolute Failure", result.getName());
		assertEquals(0, result.getSuccessPercent());

		result = table.getResult(4);
		assertEquals("Absolute Failure", result.getName());
		assertEquals(0, result.getSuccessPercent());

		result = table.getResult(5);
		assertEquals("Failure", result.getName());
		assertEquals(0, result.getSuccessPercent());

		result = table.getResult(66);
		assertEquals("Unusual Event", result.getName());
		assertEquals(0, result.getSuccessPercent());

		result = table.getResult(75);
		assertEquals("Failure", result.getName());
		assertEquals(0, result.getSuccessPercent());
	}

	@Test
	void testSuccess() {
		StaticManeuverResult result = table.getResult(76);
		assertEquals("Partial Success", result.getName());
		assertEquals(20, result.getSuccessPercent());

		result = table.getResult(90);
		assertEquals("Partial Success", result.getName());
		assertEquals(20, result.getSuccessPercent());

		result = table.getResult(91);
		assertEquals("Near Success", result.getName());
		assertEquals(80, result.getSuccessPercent());

		result = table.getResult(100);
		assertEquals("Unusual Success", result.getName());
		assertEquals(125, result.getSuccessPercent());

		result = table.getResult(110);
		assertEquals("Near Success", result.getName());
		assertEquals(80, result.getSuccessPercent());

		result = table.getResult(111);
		assertEquals("Success", result.getName());
		assertEquals(100, result.getSuccessPercent());

		result = table.getResult(175);
		assertEquals("Success", result.getName());
		assertEquals(100, result.getSuccessPercent());

		result = table.getResult(176);
		assertEquals("Absolute Success", result.getName());
		assertEquals(120, result.getSuccessPercent());
	}

	private StaticManeuverTable readTable() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/table/rmss/maneuver/static-maneuver.json")) {
			return objectMapper.readerFor(StaticManeuverTable.class).readValue(in);
		}
	}

}
