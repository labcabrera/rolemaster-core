package org.labcabrera.rolemaster.core.table.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.exception.BadRequestException;

class WeaponTableTest {

	private WeaponTable weaponTable = new WeaponTable();

	@Test
	void test150() {
		assertEquals("3CP", weaponTable.get("dagger", 150, 20));
	}

	@Test
	void test114() {
		assertEquals("3", weaponTable.get("dagger", 114, 20));
	}

	@Test
	void test113() {
		assertEquals("2", weaponTable.get("dagger", 113, 20));
	}

	@Test
	void test100() {
		assertEquals("2", weaponTable.get("dagger", 100, 20));
	}

	@Test
	void test001() {
		assertEquals("0", weaponTable.get("dagger", 1, 20));
	}

	@Test
	void testKeys() {
		List<String> weapons = weaponTable.getWeapons();
		assertNotNull(weapons);
		assertFalse(weapons.isEmpty());
	}

	@Test
	void testInvalidRangeRoll01() {
		assertThrows(BadRequestException.class, () -> {
			weaponTable.get("dagger", 0, 20);
		});
	}

	@Test
	void testInvalidRangeRoll02() {
		assertThrows(BadRequestException.class, () -> {
			weaponTable.get("dagger", 151, 20);
		});
	}

	@Test
	void testInvalidRangeArmor01() {
		assertThrows(BadRequestException.class, () -> {
			weaponTable.get("dagger", 100, 21);
		});
	}

	@Test
	void testInvalidRangeArmor02() {
		assertThrows(BadRequestException.class, () -> {
			weaponTable.get("dagger", 100, 0);
		});
	}

}
