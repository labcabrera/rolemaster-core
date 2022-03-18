package org.labcabrera.rolemaster.core.service.character;

import java.util.LinkedHashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttributeCreationService {

	@Autowired
	private RandomService randomService;

	public Integer calculateCost(Map<AttributeType, Integer> values) {
		int cost = 0;
		for (int value : values.values()) {
			cost += getCost(value);
		}
		return cost;
	}

	public Map<AttributeType, Integer> getPotentialStats(Map<AttributeType, Integer> values) {
		Map<AttributeType, Integer> result = new LinkedHashMap<>();

		return result;
	}

	public int getCost(int value) {
		if (value < 90) {
			return value;
		}
		Double tmp = Math.pow(value - 90, 2);
		return (int) (90 + tmp);
	}

	public int getPotentialStat(int value) {
		if (value == 100) {
			return 99 + randomService.dX(2);
		}
		else if (value == 99) {
			return 98 + randomService.dX(2);
		}
		else if (value == 98) {
			return 97 + randomService.dX(3);
		}
		else if (value == 97) {
			return 96 + randomService.dX(4);
		}
		else if (value == 96) {
			return 95 + randomService.dX(5);
		}
		else if (value == 95) {
			return 94 + randomService.dX(6);
		}
		else if (value == 94) {
			return 93 + randomService.dX(7);
		}
		else if (value == 93) {
			return 92 + randomService.dX(8);
		}
		else if (value == 92) {
			return 91 + randomService.dX(9);
		}
		else if (value > 84) {
			return 90 + randomService.dX(10);
		}
		else if (value > 74) {
			return Integer.max(value, 80 + d100(2));
		}
		else if (value > 64) {
			return Integer.max(value, 70 + d100(3));
		}
		else if (value > 54) {
			return Integer.max(value, 60 + d100(4));
		}
		else if (value > 44) {
			return Integer.max(value, 50 + d100(5));
		}
		else if (value > 34) {
			return Integer.max(value, 40 + d100(6));
		}
		else if (value > 24) {
			return Integer.max(value, 30 + d100(7));
		}
		else {
			return Integer.max(value, 20 + d100(8));
		}
	}

	private int d100(int count) {
		int result = 0;
		for (int i = 0; i < count; i++) {
			result += randomService.d100();
		}
		return result;
	}

}
