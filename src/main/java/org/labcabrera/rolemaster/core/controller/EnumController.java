package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.dto.NamedKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;

@RequestMapping("/enumerations")
@Tag(name = "Enumerations", description = "Enumeration values.")
public interface EnumController {

	@GetMapping("/{id}")
	Flux<NamedKey> getEnumValues(@PathVariable("id") String enumName);

}
