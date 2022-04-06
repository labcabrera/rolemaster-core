package org.labcabrera.rolemaster.core.table.weapon;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.labcabrera.rolemaster.core.exception.BadRequestException;

public class WeaponTable {

	// weaponId - roll - armor - result
	private Map<String, Map<Integer, Map<Integer, String>>> values = new HashMap<>();

	public String get(String weaponId, Integer roll, Integer armor) {
		if (roll > 150 || roll < 1) {
			throw new BadRequestException("Invalid range roll " + roll + " (1-150)");
		}
		if (armor > 20 || armor < 1) {
			throw new BadRequestException("Invalid armor type " + roll + " (1-20)");
		}
		if (!values.containsKey(weaponId)) {
			loadFromFile(weaponId);
		}
		try {
			return values.get(weaponId).get(roll).get(armor);
		}
		catch (Exception ex) {
			throw new RuntimeException("Missing weapon data");
		}
	}

	private void loadFromFile(String weaponId) {
		String resource = "data/populator/weapons/tables/" + weaponId + ".csv";
		Map<Integer, Map<Integer, String>> map = new LinkedHashMap<>();
		try (
			InputStream in = getClass().getClassLoader().getResourceAsStream(resource);
			Scanner scanner = new Scanner(in);) {
			checkHeaderFormat(weaponId, scanner.nextLine());
			while (scanner.hasNextLine()) {
				getRecordFromLine(map, scanner.nextLine());
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		values.put(weaponId, map);
	}

	private void getRecordFromLine(Map<Integer, Map<Integer, String>> map, String nextLine) {
		String[] x = nextLine.split(",");
		int roll = Integer.parseInt(x[0]);
		int armor;
		String value;
		Map<Integer, String> values = new LinkedHashMap<>();
		for (int i = 1; i < x.length; i++) {
			armor = 21 - i;
			value = x[i];
			values.put(armor, value);
		}
		map.put(roll, values);
	}

	private void checkHeaderFormat(String weaponId, String header) {
		String check = weaponId + ",20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1";
		if (!check.equals(header)) {
			throw new RuntimeException("Invalid header for weapon " + weaponId);
		}
	}

}
