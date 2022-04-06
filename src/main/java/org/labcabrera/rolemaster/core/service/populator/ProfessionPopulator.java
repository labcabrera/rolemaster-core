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
			"data/populator/professions/professions.json",
			"data/populator/professions/professions-arcane.json",
			"data/populator/professions/professions-channeling.json",
			"data/populator/professions/professions-essence.json",
			"data/populator/professions/professions-mentalism.json",
			"data/populator/professions/professions-shared.json");
	}

	@Override
	protected TypeReference<List<Profession>> getTypeReference() {
		return new TypeReference<List<Profession>>() {
		};
	}
}
