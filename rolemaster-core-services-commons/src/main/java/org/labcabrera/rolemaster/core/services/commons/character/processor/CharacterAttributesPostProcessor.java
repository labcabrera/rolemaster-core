package org.labcabrera.rolemaster.core.services.commons.character.processor;

import java.util.List;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.AttributeBonusType;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.services.character.creation.AttributeCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@ConditionalOnBean(AttributeCreationService.class)
@Order(CharacterUpdatePostProcessor.Orders.ATTRIBUTE)
@Slf4j
class CharacterAttributesPostProcessor implements CharacterUpdatePostProcessor {

	@Autowired
	private List<AttributeCreationService> attributeCreationServices;

	@Override
	public void accept(CharacterModificationContext context) {
		CharacterInfo character = context.getCharacter();
		RolemasterVersion version = character.getVersion();
		log.debug("Processing character {}", character.getName());
		character.getAttributes().entrySet().forEach(entry -> {
			AttributeType attributeType = entry.getKey();
			int currentValue = entry.getValue().getCurrentValue();
			int raceBonus = context.getRace().getAttributeModifiers().getOrDefault(attributeType, 0);
			int baseBonus = getAttributeBonus(version, currentValue);
			CharacterAttribute ca = entry.getValue();
			ca.getBonus().put(AttributeBonusType.ATTRIBUTE, baseBonus);
			ca.getBonus().put(AttributeBonusType.RACE, raceBonus);
			checkPotentialValue(version, ca);
		});
	}

	private int getAttributeBonus(RolemasterVersion version, int current) {
		return route(version).getBonus(current);
	}

	private void checkPotentialValue(RolemasterVersion version, CharacterAttribute ca) {
		if (ca.getPotentialValue() == null) {
			int potentialValue = route(version).getPotentialStat(ca.getCurrentValue());
			ca.setPotentialValue(potentialValue);
		}
	}

	private AttributeCreationService route(RolemasterVersion version) {
		return attributeCreationServices.stream().filter(e -> e.compatibleVersions().contains(version)).findFirst().orElseThrow();
	}

}
