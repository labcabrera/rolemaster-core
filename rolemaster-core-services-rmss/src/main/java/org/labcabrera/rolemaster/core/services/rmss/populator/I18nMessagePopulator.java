package org.labcabrera.rolemaster.core.services.rmss.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.i18n.I18nMessage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class I18nMessagePopulator extends AbstractJsonPopulator<I18nMessage> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList("data/populator/i18n-messages.json");
	}

	@Override
	protected TypeReference<List<I18nMessage>> getTypeReference() {
		return new TypeReference<List<I18nMessage>>() {
		};
	}

}
