package org.labcabrera.rolemaster.core.service.character.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.service.character.processor.CharacterBaseMovementRatePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CharacterBaseMovementRateAdapterTest {

	@Autowired
	private CharacterBaseMovementRatePostProcessor adapter;

	@Test
	void test01() {
		CharacterInfo character = CharacterInfo.builder()
			.height(174)
			.build();
		character.getAttributes().put(AttributeType.QUICKNESS, CharacterAttribute.builder().totalBonus(5).build());
		adapter.accept(character);
		assertEquals(18, character.getBaseMovementRate());
	}

	@Test
	void test02() {
		CharacterInfo character = CharacterInfo.builder()
			.height(184)
			.build();
		character.getAttributes().put(AttributeType.QUICKNESS, CharacterAttribute.builder().totalBonus(10).build());
		adapter.accept(character);
		assertEquals(24, character.getBaseMovementRate());
	}

}
