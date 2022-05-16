package org.labcabrera.rolemaster.core.table.rmss.character;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExperienceLevelTableTest {

	ExperienceLevelTable table = new ExperienceLevelTable();

	@Test
	void test01() {
		assertEquals(0, table.getMaxLevel(0L));
	}

	@Test
	void test02() {
		assertEquals(0, table.getMaxLevel(9999L));
	}

	@Test
	void test03() {
		assertEquals(1, table.getMaxLevel(10000L));
	}

	@Test
	void testM01() {
		assertEquals(19, table.getMaxLevel(499999L));
	}

	@Test
	void testM02() {
		assertEquals(20, table.getMaxLevel(500000L));
	}

	@Test
	void testM03() {
		assertEquals(20, table.getMaxLevel(549999L));
	}

	@Test
	void testM04() {
		assertEquals(21, table.getMaxLevel(550000L));
	}

	@Test
	void testM05() {
		assertEquals(21, table.getMaxLevel(599999L));
	}

	@Test
	void testM06() {
		assertEquals(22, table.getMaxLevel(600000L));
	}
	
	@Test
	void testLevelRequired() {
		assertEquals(0L, table.getRequiredExperience(0));
		assertEquals(10000L, table.getRequiredExperience(1));
		assertEquals(20000L, table.getRequiredExperience(2));
		assertEquals(30000L, table.getRequiredExperience(3));
		assertEquals(40000L, table.getRequiredExperience(4));
		assertEquals(50000L, table.getRequiredExperience(5));
		assertEquals(70000L, table.getRequiredExperience(6));
		assertEquals(90000L, table.getRequiredExperience(7));
		assertEquals(110000L, table.getRequiredExperience(8));
		assertEquals(130000L, table.getRequiredExperience(9));
		assertEquals(150000L, table.getRequiredExperience(10));
		assertEquals(180000L, table.getRequiredExperience(11));
		assertEquals(210000L, table.getRequiredExperience(12));
		assertEquals(240000L, table.getRequiredExperience(13));
		assertEquals(270000L, table.getRequiredExperience(14));
		assertEquals(300000L, table.getRequiredExperience(15));
		assertEquals(340000L, table.getRequiredExperience(16));
		assertEquals(380000L, table.getRequiredExperience(17));
		assertEquals(420000L, table.getRequiredExperience(18));
		assertEquals(460000L, table.getRequiredExperience(19));
		assertEquals(500000L, table.getRequiredExperience(20));
		assertEquals(550000L, table.getRequiredExperience(21));
	}

}
