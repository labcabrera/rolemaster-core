package org.labcabrera.rolemaster.core.table.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SkillSpecialBonusTableTest {

	private SkillSpecialBonusTable table = new SkillSpecialBonusTable();

	@Test
	void test00() {
		assertEquals(0, table.apply(0));
	}

	@Test
	void test01() {
		assertEquals(6, table.apply(1));
	}

	@Test
	void test10() {
		assertEquals(60, table.apply(10));
	}

	@Test
	void test11() {
		assertEquals(65, table.apply(11));
	}

	@Test
	void test20() {
		assertEquals(110, table.apply(20));
	}

	@Test
	void test30() {
		assertEquals(150, table.apply(30));
	}

	@Test
	void test31() {
		assertEquals(153, table.apply(31));
	}

}
