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
			"data/populator/races.json",
			"data/populator/races-men.json",
			"data/populator/races-orks.json",
			"data/populator/races-elves.json");
	}

	@Override
	protected TypeReference<List<Race>> getTypeReference() {
		return new TypeReference<List<Race>>() {
		};
	}
}
