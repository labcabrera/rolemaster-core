package org.labcabrera.rolemaster.core.service.populator;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.Race;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class RacePopulator extends AbstractJsonPopulator<Race> {

	@Override
	protected String getResource() {
		return "data/populator/races.json";
	}

	@Override
	protected TypeReference<List<Race>> getTypeReference() {
		return new TypeReference<List<Race>>() {
		};
	}
}
