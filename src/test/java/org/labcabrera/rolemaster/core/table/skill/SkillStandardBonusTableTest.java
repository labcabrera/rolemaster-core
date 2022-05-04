package org.labcabrera.rolemaster.core.table.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SkillStandardBonusTableTest {

	private SkillStandardBonusTable table = new SkillStandardBonusTable();

	@Test
	void test00() {
		assertEquals(-15, table.apply(0));
	}

	@Test
	void test01() {
		assertEquals(3, table.apply(1));
	}

	@Test
	void test10() {
		assertEquals(30, table.apply(10));
	}

	@Test
	void test11() {
		assertEquals(32, table.apply(11));
	}

	@Test
	void test20() {
		assertEquals(50, table.apply(20));
	}

	@Test
	void test21() {
		assertEquals(51, table.apply(21));
	}

	@Test
	void test30() {
		assertEquals(60, table.apply(30));
	}

	@Test
	void test31() {
		assertEquals(61, table.apply(31));
	}

	@Test
	void test32() {
		assertEquals(61, table.apply(32));
	}

	@Test
	void test40() {
		assertEquals(65, table.apply(40));
	}

}
