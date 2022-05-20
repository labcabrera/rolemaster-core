package org.labcabrera.rolemaster.core.services.commons.character.processor;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.CharacterArmor;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.table.rmss.armor.ArmorService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class CharacterArmorPostProcessorTest {

	@InjectMocks
	private CharacterArmorPostProcessor processor;

	@InjectMocks
	private ArmorService armorService = Mockito.spy(new ArmorService());

	@Spy
	private ObjectMapper objectMapper;

	@Mock
	private CharacterModificationContext context;

	@Mock
	private CharacterInfo characterInfo;

	@Mock
	private CharacterSkill characterSkill;

	@Mock
	private CharacterArmor armor;

	@Test
	void test() throws IOException {
		armorService.loadData();

		when(context.getCharacter()).thenReturn(characterInfo);
		when(characterInfo.getArmor()).thenReturn(armor);
		when(characterInfo.getSkill("armor-plate")).thenReturn(Optional.of(characterSkill));
		when(armor.getArmor()).thenReturn(20);
		when(characterSkill.getTotalBonus()).thenReturn(50);

		processor.accept(context);

		verify(armor, times(1)).setArmorPenalty(-115);
		verify(armor, times(1)).setArmorQuPenalty(30);
	}

}
