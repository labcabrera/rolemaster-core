package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Tag(name = "Character item controller", description = "Character items.")
@RequestMapping("/character-items")
public interface CharacterItemController {

	@GetMapping("/{id}")
	@Operation(summary = "Obtains an item equipped by a character.")
	Mono<CharacterItem> getItem(@PathVariable("id") String characterItemId);

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes an item equipped by a character.")
	Mono<Void> deleteItem(@PathVariable("id") String characterItemId);

	@PostMapping("/{id}/equip")
	@Operation(summary = "Changes the status of an item to equipped.")
	Mono<Void> equip(@PathVariable("id") String characterItemId);

	@PostMapping("/{id}/unequip")
	@Operation(summary = "Changes the status of an item to 'carried'.")
	Mono<Void> unequip(@PathVariable("id") String characterItemId);

	@PostMapping("/{id}/store")
	@Operation(summary = "Changes the status of an item to 'stored'.")
	Mono<Void> store(@PathVariable("id") String characterItemId);

	@PostMapping("/{id}/position/{position}")
	@Operation(summary = "Changes the status of an item to 'stored'.")
	Mono<CharacterItem> changeItemPosition(@PathVariable("id") String characterItem, @PathVariable("position") ItemPosition position);

	@GetMapping("/characters/{characterId}")
	@Operation(summary = "Updates the equipment status of a given item.")
	Flux<CharacterItem> getCharacterItems(@PathVariable("characterId") String characterId);

	@PostMapping("/characters/{characterId}")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Item created.")
	@Operation(summary = "Adds a certain item to a character.")
	Mono<CharacterItem> addItem(@PathVariable("characterId") String characterId, @RequestBody AddCharacterItem request);

}
