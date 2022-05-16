package org.labcabrera.rolemaster.core.table.weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.springframework.stereotype.Component;

@Component
public class WeaponTableService {

	private static final String[] weapons = { "broadsword", "dagger", "long-bow", "mace", "scimitar", "short-bow", "short-sword",
		"two-handed-sword", "war-hammer" };

	private static final String[] specialAttacks = { "bite", "grapple" };

	private final Map<String, WeaponTable> weaponMap = new HashMap<>();

	private final Map<String, SpecialWeaponTable> specialAttackMap = new HashMap<>();

	public WeaponTableService() {
		for (String e : weapons) {
			WeaponTable tmp = new WeaponTable();
			tmp.loadFromFile(e);
			weaponMap.put(e, tmp);
		}
		for (String e : specialAttacks) {
			SpecialWeaponTable tmp = new SpecialWeaponTable();
			tmp.loadFromFile(e);
			specialAttackMap.put(e, tmp);
		}
	}

	public String get(String weaponTableId, int armor, int tableAttackResult) {
		if (weaponMap.containsKey(weaponTableId)) {
			return weaponMap.get(weaponTableId).apply(armor, tableAttackResult);
		}
		else if (specialAttackMap.containsKey(weaponTableId)) {
			return specialAttackMap.get(weaponTableId).apply(armor, tableAttackResult);
		}
		throw new DataConsistenceException("Invalid weapon table " + weaponTableId + ".");
	}

	public List<String> getWeapons() {
		List<String> tmp = new ArrayList<>();
		tmp.addAll(weaponMap.keySet());
		tmp.addAll(specialAttackMap.keySet());
		tmp.sort((a, b) -> a.compareTo(b));
		return tmp;
	}

}
