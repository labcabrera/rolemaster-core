package org.labcabrera.rolemaster.core.service.character.item;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.springframework.stereotype.Component;

@Component
public class WeightPenaltyCalculator {

	private static final BigDecimal TEN = new BigDecimal(10);

	public <E> E apply(E data, CharacterInfo character) {
		BigDecimal weightAllowance = new BigDecimal(character.getWeight().getWeight()).divide(TEN);
		BigDecimal itemWeigth = character.getWeight().getItemWeight();
		BigDecimal relation = itemWeigth.divide(weightAllowance, 2, RoundingMode.HALF_EVEN);

		int encumbranceModifier = encumbraceModifier(relation);
		int armorQuPenalty = character.getArmorQuPenalty();
		int stBonus = character.getAttributes().get(AttributeType.STRENGTH).getTotalBonus() * 3;

		int sum = encumbranceModifier + armorQuPenalty + stBonus;
		int weightPenalty = Integer.min(0, sum);

		character.getWeight().setWeightPenalty(weightPenalty);
		return data;
	}

	private int encumbraceModifier(BigDecimal weightRelation) {
		int check = weightRelation.setScale(0, RoundingMode.UP).intValue();
		return Integer.min(0, -8 * (check - 1));
	}
}
