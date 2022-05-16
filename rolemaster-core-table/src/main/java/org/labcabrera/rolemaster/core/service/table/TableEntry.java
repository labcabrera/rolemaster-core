package org.labcabrera.rolemaster.core.service.table;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class TableEntry {

	/**
	 * Key must be in format: ">x", ">x", "min:max".
	 * @param key
	 * @param roll
	 * @return
	 */
	public static boolean checkKeyRange(String key, int roll) {
		if (key.startsWith("<")) {
			int min = Integer.parseInt(key.substring(1));
			return roll < min;

		}
		else if (key.startsWith(">")) {
			int max = Integer.parseInt(key.substring(1));
			return roll > max;
		}
		String[] tmp = key.split(":");
		int min = Integer.parseInt(tmp[0]);
		int max = Integer.parseInt(tmp[1]);
		return roll >= min && roll <= max;
	}

}