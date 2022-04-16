package org.labcabrera.rolemaster.core.table.maneuver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class ManeuverTableTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test() throws IOException {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		StaticManeuverTable table = readTable();
		assertNotNull(table);

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
		
		result = table.getResult(76);
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
			.getResourceAsStream("data/populator/maneuvers/static-maneuver.json")) {
			return objectMapper.readerFor(StaticManeuverTable.class).readValue(in);
		}
	}

}
