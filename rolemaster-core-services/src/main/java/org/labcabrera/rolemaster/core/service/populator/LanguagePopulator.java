package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.character.Language;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class LanguagePopulator extends AbstractJsonPopulator<Language> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/languages.json",
			"data/populator/languages-middle-earth.json");
	}

	@Override
	protected TypeReference<List<Language>> getTypeReference() {
		return new TypeReference<List<Language>>() {
		};
	}
}
