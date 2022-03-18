package org.labcabrera.rolemaster.core.service.character;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.springframework.stereotype.Service;

@Service
public class AttributeCreationService {

	public Integer calculateCost(Map<AttributeType, Integer> values) {
		int cost = 0;
		for (int value : values.values()) {
			cost += getCost(value);
		}
		return cost;

	}

	public int getCost(int value) {
		if (value < 90) {
			return value;
		}
		Double tmp = Math.pow(value - 90, 2);
		return (int) (90 + tmp);
	}

}
