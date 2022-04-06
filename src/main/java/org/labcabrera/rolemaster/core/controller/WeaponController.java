package org.labcabrera.rolemaster.core.controller;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@RequestMapping("/weapons")
@Tag(name = "Weapons", description = "List of spells.")
public interface WeaponController {

	@GetMapping("/loaded")
	@Operation(summary = "Gets the tables of weapons loaded in the system.")
	Mono<List<String>> getLoadedWeapons();

	@GetMapping("/tables/{weaponId}/{armor}/{roll}")
	@Operation(summary = "Gets the attack result of a specific weapon for a specific armor.")
	Mono<String> findById(
		@PathVariable("weaponId") String weaponId,
		@PathVariable("armor") @Min(1) @Max(20) Integer armor,
		@PathVariable("roll") @Min(1) @Max(150) Integer roll);

}
