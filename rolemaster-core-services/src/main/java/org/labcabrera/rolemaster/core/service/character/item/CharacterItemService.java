package org.labcabrera.rolemaster.core.service.character.item;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
public interface CharacterItemService {

	Mono<CharacterItem> getItem(@NotBlank String characterItemId);

	Mono<Void> deleteItem(@NotBlank String characterItemId);

	Mono<CharacterItem> changeItemPosition(@NotBlank String characterItem, @NotNull ItemPosition newPosition);

	Flux<CharacterItem> getCharacterItems(@NotBlank String characterId, ItemType type, ItemPosition position);

	Mono<CharacterItem> addItem(@NotBlank String characterId, @Valid AddCharacterItem request);

}