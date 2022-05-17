package org.labcabrera.rolemaster.core.services.rmu.character.creation;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributeModifier;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributeModifiers;
import org.labcabrera.rolemaster.core.dto.character.CharacterCreationAttributes;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.services.character.creation.AttributeCreationService;
import org.labcabrera.rolemaster.core.table.rmu.attribute.AttributeBonusRmuTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
class AttributeCreationServiceRmuImpl implements AttributeCreationService {

	@Autowired
	private AttributeBonusRmuTable bonusTable;

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
		return bonusTable.getBonus(value);
	}

	@Override
	public Integer getPotentialStat(int value) {
		//TODO get potential
		return value;
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
		int totalCost = modifiers.getAttributes().values().stream().map(e -> e.getCost()).reduce(0, (a, b) -> a + b);
		modifiers.setTotalCost(totalCost);
		return Mono.just(modifiers);
	}

	@Override
	public List<RolemasterVersion> compatibleVersions() {
		return Arrays.asList(RolemasterVersion.RMU);
	}
}
