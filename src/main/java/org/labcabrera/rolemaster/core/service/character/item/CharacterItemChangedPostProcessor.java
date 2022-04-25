package org.labcabrera.rolemaster.core.service.character.item;

import java.math.BigDecimal;
import java.util.List;

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

	@Autowired
	private WeightPenaltyCalculator weightPenaltyCalculator;

	@Autowired
	private ArmorCalculator armorPenaltyCalculator;

	@Autowired
	private DefensiveBonusCalculator defensiveBonusCalculator;

	@Autowired
	private ArmorResolver armorResolver;

	public <E> Mono<E> apply(E e, String characterId) {
		return Mono.zip(
			Mono.just(e),
			characterRepository.findById(characterId),
			characterItemRepository.findByCharacterId(characterId).collectList())
			.map(this::processWeigth)
			.flatMap(this::processArmor)
			.map(t -> armorPenaltyCalculator.apply(t, t.getT2()))
			.map(t -> weightPenaltyCalculator.apply(t, t.getT2()))
			.map(t -> defensiveBonusCalculator.apply(t, t.getT2()))
			.map(this::processMovementRate)
			.flatMap(tuple -> characterRepository.save(tuple.getT2()).thenReturn(e));
	}

	private <E> Tuple3<E, CharacterInfo, List<CharacterItem>> processWeigth(Tuple3<E, CharacterInfo, List<CharacterItem>> tuple) {
		List<CharacterItem> items = tuple.getT3().stream().filter(this::isCarried).toList();
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal itemWeight = BigDecimal.ZERO;
		for (CharacterItem item : items) {
			BigDecimal w = item.getWeight().multiply(new BigDecimal(item.getCount()));
			total = total.add(w);
			if (item.getType() != ItemType.ARMOR_PIECE && item.getArmorType() != ArmorItemType.ARMOR) {
				itemWeight = itemWeight.add(w);
			}
		}
		tuple.getT2().getWeight().setTotalCarriedWeight(total);
		tuple.getT2().getWeight().setItemWeight(itemWeight);
		return tuple;
	}

	private <E> Mono<Tuple3<E, CharacterInfo, List<CharacterItem>>> processArmor(Tuple3<E, CharacterInfo, List<CharacterItem>> tuple) {
		List<CharacterItem> items = tuple.getT3().stream().filter(this::isEquipedArmor).toList();
		return armorResolver.resolveArmor(tuple, tuple.getT2(), items);
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
