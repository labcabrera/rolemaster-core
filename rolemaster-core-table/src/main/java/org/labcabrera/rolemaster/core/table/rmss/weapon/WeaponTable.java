package org.labcabrera.rolemaster.core.table.rmss.weapon;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;

class WeaponTable extends AbstractWeaponTable {

	private Map<Integer, Map<Integer, String>> map = new HashMap<>();

	public String apply(Integer armor, Integer roll) {
		if (roll > 150 || roll < 1) {
			throw new BadRequestException("Invalid range roll " + roll + " (1-150)");
		}
		if (armor > 20 || armor < 1) {
			throw new BadRequestException("Invalid armor type " + roll + " (1-20)");
		}
		try {
			return map.get(roll).get(armor);
		}
		catch (Exception ex) {
			throw new DataConsistenceException("Missing weapon data for armor " + armor + " and roll " + roll + ".");
		}
	}

	public void loadFromFile(String weaponId) {
		String resource = "data/table/rmss/weapon/" + weaponId + ".csv";
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

	private void getRecordFromLine(Map<Integer, Map<Integer, String>> map, String nextLine) {
		String[] x = nextLine.split(",");
		int roll = Integer.parseInt(x[0]);
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

}
