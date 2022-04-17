package org.labcabrera.rolemaster.core.service.character.item;

import java.math.BigDecimal;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.message.Messages.Errors;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.item.ArmorItemType;
import org.labcabrera.rolemaster.core.model.item.ArmorPiece;
import org.labcabrera.rolemaster.core.model.item.Item;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.CharacterItemRepository;
import org.labcabrera.rolemaster.core.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CharacterItemServiceImpl implements CharacterItemService {

	@Autowired
	private CharacterItemRepository characterItemRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CharacterInfoRepository characterRepository;

	public Mono<CharacterItem> getItem(String characterItemId) {
		return characterItemRepository.findById(characterItemId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterItemNotFound(characterItemId))));
	}

	public Mono<Void> deleteItem(String characterItemId) {
		return characterItemRepository.findById(characterItemId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterItemNotFound(characterItemId))))
			.flatMap(characterItemRepository::delete);
	}

	public Mono<Void> equip(String characterItemId) {
		throw new NotImplementedException();
	}

	public Mono<Void> unequip(String characterItemId) {
		throw new NotImplementedException();
	}

	public Mono<Void> store(String characterItemId) {
		throw new NotImplementedException();
	}

	public Mono<CharacterItem> changeItemPosition(String characterItem, ItemPosition newPosition) {
		throw new NotImplementedException();
	}

	public Flux<CharacterItem> getCharacterItems(String characterId) {
		return characterRepository.findById(characterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(characterId))))
			.thenMany(characterItemRepository.findByCharacterId(characterId));
	}

	public Mono<CharacterItem> addItem(String characterId, AddCharacterItem request) {
		return characterRepository.findById(characterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(characterId))))
			.zipWith(itemRepository.findById(request.getItemId()))
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.itemNotFound(request.getItemId()))))
			.map(pair -> {
				validateItemPosition(pair.getT2(), request);
				return pair;
			})
			.map(pair -> {
				validateItemCollision(pair.getT2(), request);
				return pair;
			})
			.map(pair -> CharacterItem.builder()
				.characterId(characterId)
				.itemId(request.getItemId())
				.type(pair.getT2().getType())
				.name(pair.getT2().getName())
				.position(request.getPosition())
				.broken(false)
				.weigth(getWeigth(request, pair.getT2()))
				.build())
			.flatMap(characterItemRepository::save);
		//TODO recalcular pesos y otros valores que pueden cambiar
	}

	/**
	 * Check if the indicated position is valid for the provided item.
	 * @param item
	 * @param request
	 */
	private void validateItemPosition(Item item, AddCharacterItem request) {
		switch (request.getPosition()) {
		case MAIN_HAND:
			if (item.getType() != ItemType.WEAPON) {
				throw new BadRequestException(Errors.ONLY_WEAPONS_CAN_BE_EQUIPPED_IN_MAIN_HAND);
			}
			break;
		case OFF_HAND:
			boolean isWeapon = item.getType() == ItemType.WEAPON;
			boolean isShield = false;
			if (item instanceof ArmorPiece armor) {
				isShield = armor.getArmorType() == ArmorItemType.SHIELD;
			}
			if (!isWeapon && !isShield) {
				throw new BadRequestException(Errors.ONLY_WEAPONS_OR_SHIELDS_CAN_BE_EQUIPPEN_IN_OFF_HAND);
			}
			break;
		default:
			break;
		}
	}

	private void validateItemCollision(Item item, AddCharacterItem request) {
		//TODO
	}

	private BigDecimal getWeigth(AddCharacterItem request, Item item) {
		if (request.getWeigth() != null) {
			return request.getWeigth();
		}
		else if (item.getCommonWeight() != null) {
			return item.getCommonWeight().getMin().add(item.getCommonWeight().getMax()).divide(new BigDecimal(2));
		}
		return BigDecimal.ZERO;
	}

}
