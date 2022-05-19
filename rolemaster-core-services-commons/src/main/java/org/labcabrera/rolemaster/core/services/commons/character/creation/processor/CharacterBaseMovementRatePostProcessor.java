package org.labcabrera.rolemaster.core.services.commons.character.creation.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.labcabrera.rolemaster.core.table.rmss.character.StrideChartTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(CharacterUpdatePostProcessor.Orders.BASE_MOVEMENT)
@Slf4j
class CharacterBaseMovementRatePostProcessor implements CharacterUpdatePostProcessor {

	@Autowired
	private StrideChartTable strideChartTable;

	@Override
	public void accept(CharacterModificationContext context) {
		//TODO RMU integration
		
		CharacterInfo character = context.getCharacter();
		log.debug("Processing character {}", character.getName());
		int qu = character.getAttributes().get(AttributeType.QUICKNESS).getTotalBonus();

		int baseValue = 50;
		int attributeBonus = 3 * qu;
		int strideBonus = strideChartTable.getValue(character.getHeight());

		int valueInFeets = baseValue + attributeBonus + strideBonus;
		BigDecimal bd = new BigDecimal(valueInFeets).divide(BigDecimal.valueOf(3.281d), 0, RoundingMode.HALF_EVEN);
		int valueInMeters = bd.intValue();
		character.setBaseMovementRate(valueInMeters);
	}

}
