package org.labcabrera.rolemaster.core.service.character.item;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.model.character.AttributeBonusType;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterWeight;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WeightPenaltyCalculatorTest {

	@InjectMocks
	private WeightPenaltyCalculator calculator;

	@Spy
	private CharacterInfo character;

	@BeforeEach
	void setUp() {
		character.getAttributes().put(AttributeType.STRENGTH, CharacterAttribute.builder()
			.bonus(Collections.singletonMap(AttributeBonusType.ATTRIBUTE, 2))
			.build());
		character.setWeight(CharacterWeight.builder()
			.characterWeight(100)
			.itemWeight(BigDecimal.ZERO)
			.build());
		character.setArmorQuPenalty(-10);
	}

	@Test
	void test01() {
		character.getWeight().setItemWeight(new BigDecimal("31"));
		calculator.apply(null, character);
		assertEquals(-28, character.getWeight().getWeightPenalty());
	}

	@Test
	void test02() {
		calculator.apply(null, character);
		assertEquals(-4, character.getWeight().getWeightPenalty());
	}

	@Test
	void test03() {
		character.getWeight().setItemWeight(new BigDecimal("11"));
		calculator.apply(null, character);
		assertEquals(-12, character.getWeight().getWeightPenalty());
	}

}
