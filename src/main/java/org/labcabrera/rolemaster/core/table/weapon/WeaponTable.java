package org.labcabrera.rolemaster.core.table.weapon;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.exception.MissingWeaponData;
import org.springframework.stereotype.Component;

@Component
public class WeaponTable {

	private static final String[] weapons = { "broadsword", "dagger", "long-bow", "mace", "scimitar", "short-bow", "short-sword",
		"two-handed-sword", "war-hammer" };

	private final Map<String, Map<Integer, Map<Integer, String>>> values = new HashMap<>();

	public WeaponTable() {
		for (String weaponId : weapons) {
			loadFromFile(weaponId);
		}
	}

	public List<String> getWeapons() {
		List<String> list = new ArrayList<>(values.keySet());
		Collections.sort(list);
		return list;
	}

	public Map<Integer, Map<Integer, String>> getWeaponMap(String weaponId) {
		if (!values.containsKey(weaponId)) {
			throw new MissingWeaponData("Missing weapon table " + weaponId);
		}
		return values.get(weaponId);
	}

	public String get(String weaponId, Integer armor, Integer roll) {
		if (roll > 150 || roll < 1) {
			throw new BadRequestException("Invalid range roll " + roll + " (1-150)");
		}
		if (armor > 20 || armor < 1) {
			throw new BadRequestException("Invalid armor type " + roll + " (1-20)");
		}
		if (!values.containsKey(weaponId)) {
			throw new MissingWeaponData("Missing weapon table " + weaponId);
		}
		String result;
		try {
			result = values.get(weaponId).get(roll).get(armor);
		}
		catch (Exception ex) {
			throw new MissingWeaponData(weaponId, roll, armor);
		}
		if (StringUtils.isBlank(result) || "X".equals(result)) {
			throw new MissingWeaponData(weaponId, roll, armor);
		}
		return result;
	}

	private void loadFromFile(String weaponId) {
		String resource = "data/table/weapon/" + weaponId + ".csv";
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
			throw new DataConsistenceException("Error reading weapon table " + weaponId, ex);
		}
		values.put(weaponId, map);
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

	private void checkHeaderFormat(String weaponId, String header) {
		String check = weaponId + ",20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1";
		if (!check.equals(header)) {
			throw new DataConsistenceException("Invalid header for weapon table " + weaponId);
		}
	}

}
