package org.labcabrera.rolemaster.core.services.rmss.character.creation;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributeModifier;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributeModifiers;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributes;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.services.character.creation.AttributeCreationService;
import org.labcabrera.rolemaster.core.services.commons.RandomService;
import org.labcabrera.rolemaster.core.table.rmss.attribute.AttributeBonusTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
class AttributeCreationServiceRmssImpl implements AttributeCreationService {

	@Autowired
	private RandomService randomService;

	@Autowired
	private AttributeBonusTable bonusTable;

	@Override
	public Integer getBonus(int value) {
		return bonusTable.getBonus(value);
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

	@Override
	public List<RolemasterVersion> compatibleVersions() {
		return Arrays.asList(RolemasterVersion.RMSS);
	}

	@Override
	public Mono<CharacterCreationAttributeModifiers> getAttributeModifiers(CharacterCreationAttributes attributes) {
		CharacterCreationAttributeModifiers modifiers = CharacterCreationAttributeModifiers.builder()
			.attributes(new EnumMap<>(AttributeType.class))
			.build();
		for (AttributeType att : AttributeType.values()) {
			int current = attributes.getAttributes().get(att);
			int attributeBonus = bonusTable.getBonus(current);
			int cost = getCost(current);
			CharacterCreationAttributeModifier modifier = CharacterCreationAttributeModifier.builder()
				.currentValue(current)
				.attributeBonus(attributeBonus)
				.cost(cost)
				.build();
			modifiers.getAttributes().put(att, modifier);
		}
		int totalCost = modifiers.getAttributes().values().stream()
			.map(CharacterCreationAttributeModifier::getCost)
			.reduce(0, (a, b) -> a + b);
		modifiers.setTotalCost(totalCost);
		return Mono.just(modifiers);
	}

}
