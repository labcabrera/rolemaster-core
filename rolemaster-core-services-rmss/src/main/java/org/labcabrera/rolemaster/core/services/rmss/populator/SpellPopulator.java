package org.labcabrera.rolemaster.core.services.rmss.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.spell.Spell;
import org.labcabrera.rolemaster.core.services.commons.populator.AbstractJsonPopulator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class SpellPopulator extends AbstractJsonPopulator<Spell> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/spells/channeling/channeling-list-concussions-ways.json");
	}

	@Override
	protected TypeReference<List<Spell>> getTypeReference() {
		return new TypeReference<List<Spell>>() {
		};
	}
}
