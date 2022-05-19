package org.labcabrera.rolemaster.core.services.rmu.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.character.Profession;
import org.labcabrera.rolemaster.core.repository.ProfessionRepository;
import org.labcabrera.rolemaster.core.services.commons.populator.AbstractJsonPopulator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import reactor.core.publisher.Mono;

@Component
class ProfessionRmuPopulator extends AbstractJsonPopulator<Profession> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList("data/populator/rmu/professions/professions-rmu.json");
	}

	@Override
	protected List<Profession> collectValues() {
		List<Profession> list = super.collectValues();
		list.stream().forEach(e -> e.setVersion(RolemasterVersion.RMU));
		return list;
	}

	@Override
	protected Mono<Void> delete() {
		return ((ProfessionRepository) repository).deleteByVersion(RolemasterVersion.RMU);
	}

	@Override
	protected TypeReference<List<Profession>> getTypeReference() {
		return new TypeReference<List<Profession>>() {
		};
	}
}
