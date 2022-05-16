package org.labcabrera.rolemaster.core.services.rmss.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.spell.SpellList;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class SpellListPopulator extends AbstractJsonPopulator<SpellList> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/spells/channeling/channeling-closed-lists.json",
			"data/populator/spells/channeling/channeling-evil-lists.json",
			"data/populator/spells/channeling/channeling-open-lists.json",
			"data/populator/spells/channeling/channeling-profession-base-lists.json"
			);
	}

	@Override
	protected TypeReference<List<SpellList>> getTypeReference() {
		return new TypeReference<List<SpellList>>() {
		};
	}
}
