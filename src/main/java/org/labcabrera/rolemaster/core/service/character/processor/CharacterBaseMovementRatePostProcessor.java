package org.labcabrera.rolemaster.core.service.character.processor;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.table.character.StrideChartTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterPostProcessor.Orders.BASE_MOVEMENT)
@Slf4j
public class CharacterBaseMovementRatePostProcessor implements CharacterPostProcessor {

	@Autowired
	private StrideChartTable strideChartTable;

	@Override
	public void accept(CharacterInfo character) {
		log.debug("Processing character {}", character.getName());
		int qu = character.getAttributes().get(AttributeType.QUICKNESS).getTotalBonus();

		int baseValue = 50;
		int attributeBonus = 3 * qu;
		int strideBonus = strideChartTable.getValue(character.getHeight());

		int valueInFeets = baseValue + attributeBonus + strideBonus;
		double valueInMetersD = valueInFeets / 3.281;
		int valueInMeters = (int) valueInMetersD;
		character.setBaseMovementRate(valueInMeters);
	}

}
