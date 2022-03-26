package org.labcabrera.rolemaster.core.service.populator;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.Profession;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProfessionPopulator extends AbstractJsonPopulator<Profession> {

	@Override
	protected String getResource() {
		return "data/populator/professions.json";
	}

	@Override
	protected TypeReference<List<Profession>> getTypeReference() {
		return new TypeReference<List<Profession>>() {
		};
	}
}
