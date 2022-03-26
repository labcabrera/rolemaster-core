package org.labcabrera.rolemaster.core.table.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SkillCombinedBonusTableTest {

	private SkillCombinedBonusTable table = new SkillCombinedBonusTable();

	@Test
	void test00() {
		assertEquals(-15, table.apply(0));
	}

	@Test
	void test01() {
		assertEquals(5, table.apply(1));
	}

	@Test
	void test09() {
		assertEquals(45, table.apply(9));
	}

	@Test
	void test10() {
		assertEquals(50, table.apply(10));
	}

	@Test
	void test11() {
		assertEquals(53, table.apply(11));
	}

	@Test
	void test20() {
		assertEquals(80, table.apply(20));
	}

	@Test
	void test21() {
		assertEquals(82, table.apply(21));
	}

	@Test
	void test22() {
		assertEquals(83, table.apply(22));
	}

	@Test
	void test23() {
		assertEquals(85, table.apply(23));
	}

	@Test
	void test30() {
		assertEquals(95, table.apply(30));
	}

	@Test
	void test31() {
		assertEquals(96, table.apply(31));
	}

	@Test
	void test32() {
		assertEquals(96, table.apply(32));
	}

	@Test
	void test33() {
		assertEquals(97, table.apply(33));
	}

}
