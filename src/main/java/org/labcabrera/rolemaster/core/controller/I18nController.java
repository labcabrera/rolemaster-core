package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.i18n.I18nMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/i18n")
@Tag(name = "I18n", description = "Internationalization utils.")
public interface I18nController {

	@GetMapping("/{languageId}/categories/{categoryId}")
	@Operation(summary = "Gets the internationalization messages of a category for the specified language.")
	Flux<I18nMessage> getByCategories(
		@PathVariable("languageId") String languageId,
		@PathVariable("categoryId") String categoryId);

	@GetMapping("/{languageId}/keys/{keyId}")
	@Operation(summary = "Obtains an internationalization message specific to a given language.")
	Mono<I18nMessage> getByKey(@PathVariable("languageId") String languageId, @PathVariable("keyId") String keyId);

}
