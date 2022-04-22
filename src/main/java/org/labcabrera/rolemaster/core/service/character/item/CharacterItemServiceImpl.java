package org.labcabrera.rolemaster.core.service.character.item;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
public class CharacterItemServiceImpl implements CharacterItemService {

	@Autowired
	private CharacterItemRepository characterItemRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CharacterInfoRepository characterRepository;

	@Override
	public Mono<CharacterItem> getItem(String characterItemId) {
		return characterItemRepository.findById(characterItemId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterItemNotFound(characterItemId))));
	}

	@Override
	public Mono<Void> deleteItem(String characterItemId) {
		return characterItemRepository.findById(characterItemId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterItemNotFound(characterItemId))))
			.flatMap(characterItemRepository::delete);
		//TODO recalcular
	}

	@Override
	public Mono<CharacterItem> changeItemPosition(String characterItem, ItemPosition newPosition) {
		return characterItemRepository.findById(characterItem)
			.switchIfEmpty(Mono.error(() -> new NotFoundException("Character item not found.")))
			.flatMap(e -> Mono.just(e).zipWith(itemRepository.findById(e.getItemId())))
			.map(pair -> validateItemPosition(pair, pair.getT2(), newPosition))
			.flatMap(e -> unequipAffectedItem(e, e.getT1().getCharacterId(), e.getT2(), newPosition))
			.map(Tuple2::getT1)
			.map(item -> {
				item.setPosition(newPosition);
				item.getMetadata().setUpdated(LocalDateTime.now());
				return item;
			})
			.flatMap(characterItemRepository::save);
		//TODO recalcular
	}

	@Override
	public Flux<CharacterItem> getCharacterItems(String characterId, ItemType type, ItemPosition position) {
		return characterRepository.findById(characterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(characterId))))
			.map(e -> {
				CharacterItem probe = CharacterItem.builder()
					.characterId(characterId)
					.type(type)
					.position(position)
					.count(null)
					.broken(null)
					.build();
				return Example.of(probe);
			})
			.flatMapMany(example -> characterItemRepository.findAll(example, Sort.by(Direction.ASC, "name")));
	}

	@Override
	public Mono<CharacterItem> addItem(String characterId, AddCharacterItem request) {
		return characterRepository.findById(characterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(characterId))))
			.zipWith(itemRepository.findById(request.getItemId()))
			.switchIfEmpty(Mono.error(() -> new BadRequestException(Errors.itemNotFound(request.getItemId()))))
			.map(pair -> validateItemPosition(pair, pair.getT2(), request.getPosition()))
			.map(pair -> validateItemCount(pair, pair.getT2(), request.getCount()))
			.flatMap(pair -> unequipAffectedItem(pair, pair.getT1().getId(), pair.getT2(), request.getPosition()))
			.map(pair -> CharacterItem.builder()
				.characterId(characterId)
				.itemId(request.getItemId())
				.type(pair.getT2().getType())
				.armorType((pair.getT2()instanceof ArmorPiece ap) ? ap.getArmorType() : null)
				.name(pair.getT2().getName())
				.position(request.getPosition())
				.count(request.getCount())
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
	private <E> E validateItemPosition(E data, Item item, ItemPosition position) {
		switch (position) {
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
		return data;
	}

	private <E> E validateItemCount(E data, Item item, Integer count) {
		if (Boolean.FALSE.equals(item.getMultipleItem()) && count > 1) {
			throw new BadRequestException("Item dont allow multiple values.");
		}
		return data;
	}

	private <E> Mono<E> unequipAffectedItem(E data, String characterId, Item item, ItemPosition position) {
		if (position == ItemPosition.CARRIED || position == ItemPosition.STORED) {
			return Mono.just(data);
		}
		return Mono.just(data)
			.zipWith(characterItemRepository.findByCharacterIdAndPosition(characterId, position).collectList())
			.flatMap(pair -> {
				List<CharacterItem> sameSlotItems = pair.getT2();
				if (sameSlotItems.isEmpty()) {
					return Mono.just(data);
				}
				if (position == ItemPosition.MAIN_HAND || position == ItemPosition.OFF_HAND) {
					sameSlotItems.stream().forEach(i -> i.setPosition(ItemPosition.CARRIED));
					return characterItemRepository.saveAll(sameSlotItems).then(Mono.just(data));
				}
				if (position == ItemPosition.EQUIPED && item instanceof ArmorPiece ap) {
					if (ap.getArmorType() == ArmorItemType.HELMET || ap.getArmorType() == ArmorItemType.ARMOR) {
						sameSlotItems.stream()
							.filter(e -> e.getArmorType() == ap.getArmorType())
							.forEach(i -> i.setPosition(ItemPosition.CARRIED));
						return characterItemRepository.saveAll(sameSlotItems).then(Mono.just(data));
					}
				}
				return Mono.just(data);
			});
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
