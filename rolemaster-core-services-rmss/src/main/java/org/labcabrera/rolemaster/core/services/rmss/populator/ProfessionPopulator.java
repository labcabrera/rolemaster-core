package org.labcabrera.rolemaster.core.services.rmss.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.character.Profession;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class ProfessionPopulator extends AbstractJsonPopulator<Profession> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/professions/professions-cleric.json",
			"data/populator/professions/professions-dabbler.json",
			"data/populator/professions/professions-fighter.json",
			"data/populator/professions/professions-healer.json",
			"data/populator/professions/professions-layman.json",
			"data/populator/professions/professions-magent.json",
			"data/populator/professions/professions-magician.json",
			"data/populator/professions/professions-mentalist.json",
			"data/populator/professions/professions-ranger.json",
			"data/populator/professions/professions-rogue.json",
			"data/populator/professions/professions-sorcerer.json",
			"data/populator/professions/professions-thief.json"

		//"data/populator/professions/professions.json",
		//"data/populator/professions/professions-arcane.json",
		//"data/populator/professions/professions-channeling.json",
		//"data/populator/professions/professions-essence.json",
		//"data/populator/professions/professions-mentalism.json",
		//"data/populator/professions/professions-shared.json"
		);
	}

	@Override
	protected TypeReference<List<Profession>> getTypeReference() {
		return new TypeReference<List<Profession>>() {
		};
	}
}
