package org.labcabrera.rolemaster.core.service.character.item;

import java.math.BigDecimal;

import org.labcabrera.rolemaster.core.dto.AddCharacterItem;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.message.Messages.Errors;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
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
import reactor.util.function.Tuple2;

@Service
public class CharacterItemService {

	@Autowired
	private CharacterItemRepository characterItemRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CharacterInfoRepository characterRepository;

	public Flux<CharacterItem> getItems(String characterId) {
		return characterItemRepository.findByCharacterId(characterId);
	}

	public Mono<CharacterItem> addItem(String characterId, AddCharacterItem request) {
		return characterRepository.findById(characterId)
			.switchIfEmpty(Mono.error(() -> new NotFoundException(Errors.characterNotFound(characterId))))
			.zipWith(itemRepository.findById(request.getItemId()))
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Item not found")))
			.map(pair -> validateItemPosition(pair, request))
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
	}

	private Tuple2<CharacterInfo, Item> validateItemPosition(Tuple2<CharacterInfo, Item> tuple, AddCharacterItem request) {
		Item item = tuple.getT2();
		switch (request.getPosition()) {
		case MAIN_HAND:
			if (item.getType() != ItemType.WEAPON) {
				throw new BadRequestException("Only weapons can be equipped in main hand.");
			}
			break;
		case OFF_HAND:
			boolean isWeapon = item.getType() == ItemType.WEAPON;
			boolean isShield = false;
			if (item instanceof ArmorPiece armor) {
				isShield = armor.getArmorType() == ArmorItemType.SHIELD;
			}
			if (!isWeapon && !isShield) {
				throw new BadRequestException("Only weapons or shields can be equippen in off-hand.");
			}
			break;
		default:
			break;
		}
		return tuple;
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
