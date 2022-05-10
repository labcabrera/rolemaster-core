package org.labcabrera.rolemaster.core.table.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.DataConsistenceException;

class WeaponTableServiceTest {

	private WeaponTableService weaponTable = new WeaponTableService();

	@Test
	void test150() {
		assertEquals("3CP", weaponTable.get("dagger", 20, 150));
	}

	@Test
	void test114() {
		assertEquals("3", weaponTable.get("dagger", 20, 114));
	}

	@Test
	void test113() {
		assertEquals("2", weaponTable.get("dagger", 20, 113));
	}

	@Test
	void test100() {
		assertEquals("2", weaponTable.get("dagger", 20, 100));
	}

	@Test
	void test001() {
		assertEquals("0", weaponTable.get("dagger", 20, 1));
	}

	@Test
	void testKeys() {
		List<String> weapons = weaponTable.getWeapons();
		assertNotNull(weapons);
		assertFalse(weapons.isEmpty());
	}

	@Test
	void testInvalidWeapon() {
		assertThrows(DataConsistenceException.class, () -> {
			weaponTable.get("invalid-weapon-id", 20, 150);
		});
	}

	@Test
	void testInvalidRangeRoll01() {
		assertThrows(BadRequestException.class, () -> {
			weaponTable.get("dagger", 20, 0);
		});
	}

	@Test
	void testInvalidRangeRoll02() {
		assertThrows(BadRequestException.class, () -> {
			weaponTable.get("dagger", 20, 151);
		});
	}

	@Test
	void testInvalidRangeArmor01() {
		assertThrows(BadRequestException.class, () -> {
			weaponTable.get("dagger", 21, 100);
		});
	}

	@Test
	void testInvalidRangeArmor02() {
		assertThrows(BadRequestException.class, () -> {
			weaponTable.get("dagger", 0, 100);
		});
	}

}
