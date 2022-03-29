package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.character.Profession;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class ProfessionPopulator extends AbstractJsonPopulator<Profession> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/professions.json",
			"data/populator/professions-channeling.json");
	}

	@Override
	protected TypeReference<List<Profession>> getTypeReference() {
		return new TypeReference<List<Profession>>() {
		};
	}
}
