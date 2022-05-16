package org.labcabrera.rolemaster.core.services.rmss.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.Universe;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class UniversePopulator extends AbstractJsonPopulator<Universe> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/universes.json");
	}

	@Override
	protected TypeReference<List<Universe>> getTypeReference() {
		return new TypeReference<List<Universe>>() {
		};
	}
}
