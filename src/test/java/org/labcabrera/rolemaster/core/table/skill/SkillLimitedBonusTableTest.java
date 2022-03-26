package org.labcabrera.rolemaster.core.table.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SkillLimitedBonusTableTest {

	private SkillLimitedBonusTable table = new SkillLimitedBonusTable();

	@Test
	void test00() {
		assertEquals(0, table.apply(0));
	}

	@Test
	void test01() {
		assertEquals(1, table.apply(1));
	}

	@Test
	void test20() {
		assertEquals(20, table.apply(20));
	}

	@Test
	void test21() {
		assertEquals(21, table.apply(21));
	}

	@Test
	void test22() {
		assertEquals(21, table.apply(22));
	}

	@Test
	void test23() {
		assertEquals(22, table.apply(23));
	}

	@Test
	void test28() {
		assertEquals(24, table.apply(28));
	}

	@Test
	void test29() {
		assertEquals(25, table.apply(29));
	}

	@Test
	void test30() {
		assertEquals(25, table.apply(30));
	}

	@Test
	void test31() {
		assertEquals(25, table.apply(31));
	}

	@Test
	void test32() {
		assertEquals(25, table.apply(32));
	}

}
