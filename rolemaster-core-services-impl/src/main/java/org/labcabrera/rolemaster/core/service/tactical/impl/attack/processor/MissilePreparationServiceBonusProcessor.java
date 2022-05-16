package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class MissilePreparationServiceBonusProcessor {

	private final Map<String, List<Integer>> map;

	public MissilePreparationServiceBonusProcessor() {
		map = new HashMap<>();
		map.put("short-bow", Arrays.asList(10, 0, 0, 0));
		map.put("composite-bow", Arrays.asList(20, 0, 0, 0));
		map.put("long-bow", Arrays.asList(30, 0, 0, 0));
		map.put("ligth-crossbow", Arrays.asList(-1, 20, 0, 0));
		map.put("heavy-crossbow", Arrays.asList(-1, 30, 10, 0));
		map.put("sling", Arrays.asList(-1, 30, 10, 0));
	}

	public int getPreparationBonus(String weaponId, int preparationRounds) {
		if (preparationRounds < 0 || preparationRounds > 3) {
			throw new BadRequestException("Invalid preparation rounds.");
		}
		if (!map.containsKey(weaponId)) {
			throw new BadRequestException("Unsupported weapon " + weaponId + ".");
		}
		int result = map.get(weaponId).get(preparationRounds);
		if (result < 0) {
			throw new BadRequestException("Weapon cant be fired.");
		}
		return result;
	}

}
