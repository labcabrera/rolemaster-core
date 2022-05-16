package org.labcabrera.rolemaster.core.service.table.weapon;

import java.util.function.BiFunction;

import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;

abstract class AbstractWeaponTable implements BiFunction<Integer, Integer, String> {

	protected void checkHeaderFormat(String weaponId, String header) {
		String check = weaponId + ",20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1";
		if (!check.equals(header)) {
			throw new DataConsistenceException("Invalid header for weapon table " + weaponId);
		}
	}
}
