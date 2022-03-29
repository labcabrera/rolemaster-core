package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.spell.Spell;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class SpellPopulator extends AbstractJsonPopulator<Spell> {

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
