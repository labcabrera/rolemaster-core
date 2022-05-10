package org.labcabrera.rolemaster.core.table.weapon;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.function.BiFunction;

import org.labcabrera.rolemaster.core.exception.DataConsistenceException;

class SpecialWeaponTable implements BiFunction<Integer, Integer, String> {

	private Map<String, Map<Integer, String>> map;

	@Override
	public String apply(Integer armor, Integer roll) {
		for (Entry<String, Map<Integer, String>> entry : map.entrySet()) {
			if (checkRoll(entry.getKey(), roll)) {
				return entry.getValue().get(armor);
			}
		}
		throw new DataConsistenceException("Missing result for amor " + armor + " and roll " + roll);
	}

	public void loadFromFile(String weaponId) {
		String resource = "data/table/weapon/special-attack-" + weaponId + ".csv";
		map = new LinkedHashMap<>();
		try (
			InputStream in = getClass().getClassLoader().getResourceAsStream(resource);
			Scanner scanner = new Scanner(in);) {
			checkHeaderFormat(weaponId, scanner.nextLine());
			while (scanner.hasNextLine()) {
				getRecordFromLine(map, scanner.nextLine());
			}
		}
		catch (Exception ex) {
			throw new DataConsistenceException("Error reading weapon table " + weaponId, ex);
		}
	}

	private void getRecordFromLine(Map<String, Map<Integer, String>> map, String nextLine) {
		String[] x = nextLine.split(",");
		String roll = x[0];
		int armor;
		String value;
		Map<Integer, String> tmp = new LinkedHashMap<>();
		for (int i = 1; i < x.length; i++) {
			armor = 21 - i;
			value = x[i];
			tmp.put(armor, value);
		}
		map.put(roll, tmp);
	}

	private void checkHeaderFormat(String weaponId, String header) {
		String check = weaponId + ",20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1";
		if (!check.equals(header)) {
			throw new DataConsistenceException("Invalid header for weapon table " + weaponId);
		}
	}

	private boolean checkRoll(String key, int roll) {
		String[] split = key.split("-");
		int min = Integer.valueOf(split[0]);
		int max = Integer.valueOf(split[1]);
		return roll >= min && roll <= max;
	}

}
