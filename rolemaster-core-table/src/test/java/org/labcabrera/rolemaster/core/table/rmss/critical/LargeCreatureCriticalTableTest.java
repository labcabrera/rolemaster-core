package org.labcabrera.rolemaster.core.table.rmss.critical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.combat.LargeCreatureCriticalType;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class LargeCreatureCriticalTableTest {

	@Autowired
	private ObjectMapper objectMapper;

	private LargeCreatureCriticalTable table;

	@BeforeEach
	void test() throws IOException {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		table = readTable();
		assertNotNull(table);
	}

	@Test
	void testNormal00() {
		assertThrows(BadRequestException.class, () -> table.get(LargeCreatureCriticalType.NORMAL, 0));
	}

	@Test
	void testNormal01() {
		CriticalTableResult result = table.get(LargeCreatureCriticalType.NORMAL, 1);
		assertEquals(12, result.getHp());
	}

	@Test
	void testNormal66() {
		CriticalTableResult result = table.get(LargeCreatureCriticalType.NORMAL, 66);
		assertEquals(6, result.getDebuffs().get(Debuff.MORTAL_DAMAGE));
	}

	@Test
	void testNormal251() {
		CriticalTableResult result = table.get(LargeCreatureCriticalType.NORMAL, 251);
		assertEquals(35, result.getHp());
		assertEquals(3, result.getDebuffs().get(Debuff.STUNNED));
		assertEquals(3, result.getDebuffs().get(Debuff.CANT_PARRY));
	}

	private LargeCreatureCriticalTable readTable() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/table/rmss/critical/critical-table-large-creature.json")) {
			return objectMapper.readerFor(LargeCreatureCriticalTable.class).readValue(in);
		}
	}

}
