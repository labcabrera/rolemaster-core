package org.labcabrera.rolemaster.core.service.table.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SkillLimitedBonusTableTest {

	private SkillLimitedBonusTable table = new SkillLimitedBonusTable();

	@Test
	void test00() {
		assertEquals(0, table.applyAsInt(0));
	}

	@Test
	void test01() {
		assertEquals(1, table.applyAsInt(1));
	}

	@Test
	void test20() {
		assertEquals(20, table.applyAsInt(20));
	}

	@Test
	void test21() {
		assertEquals(21, table.applyAsInt(21));
	}

	@Test
	void test22() {
		assertEquals(21, table.applyAsInt(22));
	}

	@Test
	void test23() {
		assertEquals(22, table.applyAsInt(23));
	}

	@Test
	void test28() {
		assertEquals(24, table.applyAsInt(28));
	}

	@Test
	void test29() {
		assertEquals(25, table.applyAsInt(29));
	}

	@Test
	void test30() {
		assertEquals(25, table.applyAsInt(30));
	}

	@Test
	void test31() {
		assertEquals(25, table.applyAsInt(31));
	}

	@Test
	void test32() {
		assertEquals(25, table.applyAsInt(32));
	}

}
