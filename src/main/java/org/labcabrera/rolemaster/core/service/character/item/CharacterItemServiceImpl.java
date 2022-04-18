package org.labcabrera.rolemaster.core.service.character.item;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.message.Messages.Errors;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@Slf4j
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
		//TODO recalcular
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

	public Flux<CharacterItem> getCharacterItems(String characterId, ItemType type, ItemPosition position) {
		return characterRepository.findById(characterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(characterId))))
			.map(e -> {
				CharacterItem probe = new CharacterItem();
				probe.setCharacterId(characterId);
				probe.setType(type);
				probe.setPosition(position);
				probe.setCustomizations(null);
				probe.setBroken(null);
				return Example.of(probe);
			})
			.flatMapMany(example -> characterItemRepository.findAll(example, Sort.by(Direction.ASC, "name")));
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
			.flatMap(pair -> unequipAffectedItem(pair, request.getPosition()))
			.map(pair -> CharacterItem.builder()
				.characterId(characterId)
				.itemId(request.getItemId())
				.type(pair.getT2().getType())
				.name(pair.getT2().getName())
				.position(request.getPosition())
				.broken(false)
				.weight(getWeight(request, pair.getT2()))
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
		case EQUIPED:
			if (!(item instanceof ArmorPiece)) {
				throw new BadRequestException(Errors.ONLY_ARMOR_CAN_BE_IN_POSITION_EQUIPED);
			}
			break;
		default:
			break;
		}
	}

	private Mono<Tuple2<CharacterInfo, Item>> unequipAffectedItem(Tuple2<CharacterInfo, Item> tuple, ItemPosition position) {
		if (position == ItemPosition.CARRIED || position == ItemPosition.STORED) {
			return Mono.just(tuple);
		}
		CharacterInfo character = tuple.getT1();
		return Mono.just(tuple)
			.zipWith(characterItemRepository.findByCharacterIdAndPosition(character.getId(), position).collectList())
			.flatMap(pair -> {
				List<CharacterItem> sameSlotItems = pair.getT2();
				if (sameSlotItems.isEmpty()) {
					return Mono.just(pair);
				}
				if (position == ItemPosition.MAIN_HAND || position == ItemPosition.OFF_HAND) {
					sameSlotItems.stream().forEach(e -> e.setPosition(ItemPosition.CARRIED));
					return characterItemRepository.saveAll(sameSlotItems).then(Mono.just(pair));
				}
				//TODO
				log.warn("TODO: check unequip items.");
				return Mono.just(pair);
			})
			.map(Tuple2::getT1);
	}

	private BigDecimal getWeight(AddCharacterItem request, Item item) {
		if (request.getWeight() != null) {
			return request.getWeight();
		}
		else if (item.getCommonWeight() != null) {
			return item.getCommonWeight().getMin().add(item.getCommonWeight().getMax()).divide(new BigDecimal(2));
		}
		return BigDecimal.ZERO;
	}

}
