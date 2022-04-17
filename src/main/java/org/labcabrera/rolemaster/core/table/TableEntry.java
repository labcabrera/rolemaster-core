package org.labcabrera.rolemaster.core.table;

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
			int min = Integer.valueOf(key.substring(1));
			return roll < min;

		}
		else if (key.startsWith(">")) {
			int max = Integer.valueOf(key.substring(1));
			return roll > max;
		}
		String[] tmp = key.split(":");
		int min = Integer.valueOf(tmp[0]);
		int max = Integer.valueOf(tmp[1]);
		return roll >= min && roll <= max;
	}

}
