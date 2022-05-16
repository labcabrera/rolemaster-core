package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.I18nController;
import org.labcabrera.rolemaster.core.model.i18n.I18nMessage;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class I18nControllerImpl implements I18nController {

	@Override
	public Flux<I18nMessage> getByCategories(String languageId, String categoryId) {
		return null;
	}

	@Override
	public Mono<I18nMessage> getByKey(String languageId, String keyId) {
		return null;
	}

}
