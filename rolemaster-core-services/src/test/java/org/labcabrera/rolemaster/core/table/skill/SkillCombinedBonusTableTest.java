package org.labcabrera.rolemaster.core.table.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SkillCombinedBonusTableTest {

	private SkillCombinedBonusTable table = new SkillCombinedBonusTable();

	@Test
	void test00() {
		assertEquals(-15, table.applyAsInt(0));
	}

	@Test
	void test01() {
		assertEquals(5, table.applyAsInt(1));
	}

	@Test
	void test09() {
		assertEquals(45, table.applyAsInt(9));
	}

	@Test
	void test10() {
		assertEquals(50, table.applyAsInt(10));
	}

	@Test
	void test11() {
		assertEquals(53, table.applyAsInt(11));
	}

	@Test
	void test20() {
		assertEquals(80, table.applyAsInt(20));
	}

	@Test
	void test21() {
		assertEquals(82, table.applyAsInt(21));
	}

	@Test
	void test22() {
		assertEquals(83, table.applyAsInt(22));
	}

	@Test
	void test23() {
		assertEquals(85, table.applyAsInt(23));
	}

	@Test
	void test30() {
		assertEquals(95, table.applyAsInt(30));
	}

	@Test
	void test31() {
		assertEquals(96, table.applyAsInt(31));
	}

	@Test
	void test32() {
		assertEquals(96, table.applyAsInt(32));
	}

	@Test
	void test33() {
		assertEquals(97, table.applyAsInt(33));
	}

}
