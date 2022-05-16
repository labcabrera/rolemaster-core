package org.labcabrera.rolemaster.core.service.table.weapon;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.labcabrera.rolemaster.core.exception.DataConsistenceException;

class SpecialWeaponTable extends AbstractWeaponTable {

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

	private boolean checkRoll(String key, int roll) {
		String[] split = key.split("-");
		int min = Integer.parseInt(split[0]);
		int max = Integer.parseInt(split[1]);
		return roll >= min && roll <= max;
	}

}
