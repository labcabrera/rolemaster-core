package org.labcabrera.rolemaster.core.controller;

import java.util.List;

import org.labcabrera.rolemaster.core.dto.NamedKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/enumerations")
@Tag(name = "Enumerations", description = "Enumeration values.")
public interface EnumController {

	@GetMapping
	Mono<List<String>> getEnums();

	@GetMapping("/{id}")
	Flux<NamedKey> getEnumValues(
		@Parameter(required = true, example = "realm") @PathVariable("id") String enumName);

}
