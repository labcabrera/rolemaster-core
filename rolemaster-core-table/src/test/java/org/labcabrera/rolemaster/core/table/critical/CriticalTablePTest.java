package org.labcabrera.rolemaster.core.table.critical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.tactical.Debuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class CriticalTablePTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test() throws IOException {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

		CriticalTable table = readTable();

		assertNotNull(table);

		assertEquals(1, table.get(CriticalSeverity.A, 16).getHp());
		assertEquals(1, table.get(CriticalSeverity.A, 16).getDebuffs().get(Debuff.MUST_PARRY));

	}

	private CriticalTable readTable() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("data/table/critical/critical-table-p.json")) {
			return objectMapper.readerFor(CriticalTable.class).readValue(in);
		}
	}

}
