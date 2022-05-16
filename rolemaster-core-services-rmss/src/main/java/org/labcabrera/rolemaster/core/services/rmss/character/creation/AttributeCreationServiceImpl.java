package org.labcabrera.rolemaster.core.services.rmss.character.creation;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.services.character.creation.AttributeCreationService;
import org.labcabrera.rolemaster.core.services.commons.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class AttributeCreationServiceImpl implements AttributeCreationService {

	@Autowired
	private RandomService randomService;

	@Override
	public Integer calculateCost(Map<AttributeType, Integer> values) {
		int cost = 0;
		for (int value : values.values()) {
			cost += getCost(value);
		}
		return cost;
	}

	@Override
	public Integer getCost(int value) {
		if (value < 90) {
			return value;
		}
		Double tmp = Math.pow((double) value - 90, 2);
		return (int) (90 + tmp);
	}

	@Override
	public Integer getPotentialStat(int value) {
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
			return Integer.max(value, 80 + d10Sum(2));
		}
		else if (value > 64) {
			return Integer.max(value, 70 + d10Sum(3));
		}
		else if (value > 54) {
			return Integer.max(value, 60 + d10Sum(4));
		}
		else if (value > 44) {
			return Integer.max(value, 50 + d10Sum(5));
		}
		else if (value > 34) {
			return Integer.max(value, 40 + d10Sum(6));
		}
		else if (value > 24) {
			return Integer.max(value, 30 + d10Sum(7));
		}
		else {
			return Integer.max(value, 20 + d10Sum(8));
		}
	}

	private int d10Sum(int count) {
		int result = 0;
		for (int i = 0; i < count; i++) {
			result += randomService.d10();
		}
		return result;
	}

}
