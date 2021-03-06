package org.labcabrera.rolemaster.core.api.controller;

import java.util.Set;

import org.labcabrera.rolemaster.core.dto.NamedKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/enumerations")
@Tag(name = "Enumerations", description = "Enumeration values.")
public interface EnumController {

	@GetMapping
	@Operation(summary = "Gets the list of enumerated types.")
	Mono<Set<String>> getEnums();

	@GetMapping("/{id}")
	@Operation(summary = "Gets the list of pairs (code, name) associated with an enumerated type.")
	Flux<NamedKey> getEnumValues(
		@Parameter(required = true, example = "realm") @PathVariable("id") String enumName);

}
