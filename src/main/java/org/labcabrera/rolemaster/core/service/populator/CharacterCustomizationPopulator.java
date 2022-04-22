package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.character.CharacterCustomization;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class CharacterCustomizationPopulator extends AbstractJsonPopulator<CharacterCustomization> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/character/customization/flaws-mental.json",
			"data/populator/character/customization/flaws-physical.json",
			"data/populator/character/customization/flaws-special.json",
			"data/populator/character/customization/talents-mental.json",
			"data/populator/character/customization/talents-mystical.json",
			"data/populator/character/customization/talents-physical.json",
			"data/populator/character/customization/talents-special.json",
			"data/populator/character/customization/talents-special-items.json",
			"data/populator/character/customization/talents-special-status.json",
			"data/populator/character/customization/talents-special-training.json"

		);
	}

	@Override
	protected TypeReference<List<CharacterCustomization>> getTypeReference() {
		return new TypeReference<List<CharacterCustomization>>() {
		};
	}
}
