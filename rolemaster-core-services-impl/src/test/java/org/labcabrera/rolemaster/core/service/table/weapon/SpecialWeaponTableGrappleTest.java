package org.labcabrera.rolemaster.core.service.table.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpecialWeaponTableGrappleTest {

	private SpecialWeaponTable table;

	@BeforeEach
	void prepare() {
		table = new SpecialWeaponTable();
		table.loadFromFile("grapple");
	}

	@Test
	void test20() {
		assertEquals("0", table.apply(20, 60));
		assertEquals("1AG", table.apply(20, 61));
		assertEquals("4EG", table.apply(20, 150));
	}

	@Test
	void test01() {
		assertEquals("0", table.apply(1, 60));
		assertEquals("1", table.apply(1, 82));
		assertEquals("15FG", table.apply(1, 150));
	}

}
