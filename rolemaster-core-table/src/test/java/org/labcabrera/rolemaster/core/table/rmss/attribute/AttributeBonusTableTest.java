package org.labcabrera.rolemaster.core.table.rmss.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AttributeBonusTableTest {

	private AttributeBonusTable service = new AttributeBonusTable();

	@Test
	void test102() {
		assertEquals(14, service.getBonus(102));
	}

	@Test
	void test101() {
		assertEquals(12, service.getBonus(101));
	}

	@Test
	void test100() {
		assertEquals(10, service.getBonus(100));
	}

	@Test
	void test99() {
		assertEquals(9, service.getBonus(99));
	}

	@Test
	void test98() {
		assertEquals(9, service.getBonus(98));
	}

	@Test
	void test97() {
		assertEquals(8, service.getBonus(97));
	}

	@Test
	void test96() {
		assertEquals(8, service.getBonus(96));
	}

	@Test
	void test91() {
		assertEquals(5, service.getBonus(91));
	}

	@Test
	void test90() {
		assertEquals(5, service.getBonus(90));
	}

	@Test
	void test89() {
		assertEquals(4, service.getBonus(89));
	}

	@Test
	void test84() {
		assertEquals(3, service.getBonus(84));
	}

	@Test
	void test80() {
		assertEquals(3, service.getBonus(80));
	}

	@Test
	void test79() {
		assertEquals(2, service.getBonus(79));
	}

	@Test
	void test75() {
		assertEquals(2, service.getBonus(75));
	}

	@Test
	void test74() {
		assertEquals(1, service.getBonus(74));
	}

	@Test
	void test70() {
		assertEquals(1, service.getBonus(70));
	}

	@Test
	void test69() {
		assertEquals(0, service.getBonus(69));
	}

	@Test
	void test31() {
		assertEquals(0, service.getBonus(31));
	}

	@Test
	void test30() {
		assertEquals(-1, service.getBonus(30));
	}

	@Test
	void test26() {
		assertEquals(-1, service.getBonus(26));
	}

	@Test
	void test25() {
		assertEquals(-2, service.getBonus(25));
	}

	@Test
	void test11() {
		assertEquals(-4, service.getBonus(11));
	}

	@Test
	void test10() {
		assertEquals(-5, service.getBonus(10));
	}

	@Test
	void test05() {
		assertEquals(-8, service.getBonus(5));
	}

	@Test
	void test04() {
		assertEquals(-8, service.getBonus(4));
	}

	@Test
	void test03() {
		assertEquals(-9, service.getBonus(3));
	}

	@Test
	void test02() {
		assertEquals(-9, service.getBonus(2));
	}

	@Test
	void test01() {
		assertEquals(-10, service.getBonus(1));
	}

}
