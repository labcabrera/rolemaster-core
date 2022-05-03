package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.character.Race;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class RacePopulator extends AbstractJsonPopulator<Race> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/races/races.json",
			"data/populator/races/races-men.json",
			"data/populator/races/races-orcs.json",
			"data/populator/races/races-elves.json",
			"data/populator/races/races-middle-earth.json",
			"data/populator/races/races-middle-earth-orcs.json");
	}

	@Override
	protected TypeReference<List<Race>> getTypeReference() {
		return new TypeReference<List<Race>>() {
		};
	}
}
