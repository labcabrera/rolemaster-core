package org.labcabrera.rolemaster.core.service.populator;

import java.util.List;

import org.labcabrera.rolemaster.core.model.i18n.I18nMessage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class I18nMessagePopulator extends AbstractJsonPopulator<I18nMessage> {

	@Override
	protected String getResource() {
		return "data/populator/i18n-messages.json";
	}

	@Override
	protected TypeReference<List<I18nMessage>> getTypeReference() {
		return new TypeReference<List<I18nMessage>>() {
		};
	}

}
