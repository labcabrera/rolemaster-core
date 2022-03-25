package org.labcabrera.rolemaster.core.service.character.adapter;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.labcabrera.rolemaster.core.table.skill.character.StrideChartTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CharacterBaseMovementRateAdapter implements CharacterAdapter {

	@Autowired
	private StrideChartTable strideChartTable;

	@Override
	public CharacterModificationContext apply(CharacterModificationContext context) {
		int qu = context.getCharacter().getAttributes().get(AttributeType.QUICKNESS).getTotalBonus();

		int baseValue = 50;
		int attributeBonus = 3 * qu;
		int strideBonus = strideChartTable.getValue(context.getCharacter().getHeight());

		int valueInFeets = baseValue + attributeBonus + strideBonus;
		double valueInMetersD = valueInFeets / 3.281;
		int valueInMeters = (int) valueInMetersD;
		context.getCharacter().setBaseMovementRate(valueInMeters);
		return context;
	}

}
