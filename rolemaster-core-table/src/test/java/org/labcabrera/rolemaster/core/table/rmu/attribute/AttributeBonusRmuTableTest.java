package org.labcabrera.rolemaster.core.table.rmu.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AttributeBonusRmuTableTest {

	@Autowired
	private AttributeBonusRmuTable table;

	@Test
	void test() {
		assertEquals(-15, table.getBonus(0));
		assertEquals(-15, table.getBonus(1));

		assertEquals(15, table.getBonus(100));
		assertEquals(15, table.getBonus(101));

	}

}
