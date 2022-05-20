package org.labcabrera.rolemaster.core.services.rmss.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.repository.RaceRepository;
import org.labcabrera.rolemaster.core.services.commons.populator.AbstractJsonPopulator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import reactor.core.publisher.Mono;

@Component
class RacePopulator extends AbstractJsonPopulator<Race> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/races/races.json",
			"data/populator/races/races-men.json",
			"data/populator/races/races-orcs.json",
			"data/populator/races/races-elves.json",
			"data/populator/races/races-middle-earth.json",
			"data/populator/races/races-middle-earth-dwarfs.json",
			"data/populator/races/races-middle-earth-elves.json",
			"data/populator/races/races-middle-earth-men.json",
			"data/populator/races/races-middle-earth-orcs.json");
	}

	@Override
	protected List<Race> collectValues() {
		List<Race> list = super.collectValues();
		list.stream().forEach(e -> e.setVersion(RolemasterVersion.RMSS));
		return list;
	}

	@Override
	protected Mono<Void> delete() {
		return ((RaceRepository) repository).deleteByVersion(RolemasterVersion.RMSS);
	}

	@Override
	protected TypeReference<List<Race>> getTypeReference() {
		return new TypeReference<List<Race>>() {
		};
	}
}
