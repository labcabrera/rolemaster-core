package org.labcabrera.rolemaster.core.service.character.item;

import java.math.BigDecimal;
import java.util.List;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.item.ArmorItemType;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.CharacterItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@Component
public class CharacterItemChangedPostProcessor {

	@Autowired
	private CharacterInfoRepository characterRepository;

	@Autowired
	private CharacterItemRepository characterItemRepository;

	public <E> Mono<E> apply(E e, String characterId) {
		return Mono.zip(
			Mono.just(e),
			characterRepository.findById(characterId),
			characterItemRepository.findByCharacterId(characterId).collectList())
			.map(this::processWeigth)
			.map(this::processArmor)
			.map(this::processArmorPenalty)
			.map(this::processMovementRate)
			.flatMap(tuple -> characterRepository.save(tuple.getT2()).thenReturn(e));
	}

	private <E> Tuple3<E, CharacterInfo, List<CharacterItem>> processWeigth(Tuple3<E, CharacterInfo, List<CharacterItem>> tuple) {
		List<CharacterItem> items = tuple.getT3().stream().filter(this::isCarried).toList();
		BigDecimal total = BigDecimal.ZERO;
		for (CharacterItem item : items) {
			BigDecimal w = item.getWeight().multiply(new BigDecimal(item.getCount()));
			total = total.add(w);
		}
		tuple.getT2().setCarriedWeight(total);
		return tuple;
	}

	private <E> Tuple3<E, CharacterInfo, List<CharacterItem>> processArmor(Tuple3<E, CharacterInfo, List<CharacterItem>> tuple) {
		List<CharacterItem> items = tuple.getT3().stream().filter(this::isEquipedArmor).toList();
		int at = 1;
		List<CharacterItem> armors = items.stream().filter(e -> e.getArmorType() == ArmorItemType.ARMOR).toList();
		if (armors.size() > 1) {
			throw new BadRequestException("Multiple armors equiped");
		}
		else if (armors.size() == 1) {
			CharacterItem armor = armors.iterator().next();
			//TODO check at customization
			switch (armor.getItemId()) {
			default:
				at = 8;
			}
		}
		tuple.getT2().setArmor(at);
		return tuple;
	}

	private <E> Tuple3<E, CharacterInfo, List<CharacterItem>> processArmorPenalty(Tuple3<E, CharacterInfo, List<CharacterItem>> tuple) {
		//TODO armor penalty
		return tuple;
	}

	private <E> Tuple3<E, CharacterInfo, List<CharacterItem>> processMovementRate(Tuple3<E, CharacterInfo, List<CharacterItem>> tuple) {
		//TODO movement rate
		return tuple;
	}

	private boolean isCarried(CharacterItem item) {
		return item.getPosition() != ItemPosition.STORED;
	}

	private boolean isEquipedArmor(CharacterItem item) {
		return item.getPosition() == ItemPosition.EQUIPED && item.getType() == ItemType.ARMOR_PIECE;
	}

}
