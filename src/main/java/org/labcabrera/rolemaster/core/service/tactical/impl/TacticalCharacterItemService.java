package org.labcabrera.rolemaster.core.service.tactical.impl;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItemFeature;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItemFeatureType;
import org.labcabrera.rolemaster.core.model.item.ArmorItemType;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.labcabrera.rolemaster.core.repository.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalCharacterItemService {

	@Autowired
	private WeaponRepository weaponRepository;

	public int getShieldBonus(CharacterItem item) {
		if (item == null) {
			return 0;
		}
		String feature = getFeature(item, CharacterItemFeatureType.SHIELD_BONUS);
		if (feature != null) {
			return Integer.parseInt(feature);
		}
		if (item.getType() == ItemType.ARMOR_PIECE && item.getArmorType() == ArmorItemType.SHIELD) {
			return getShieldBonus(item.getItemId());
		}
		if (item.getType() == ItemType.WEAPON) {
			int result = 5;
			if ("maine-gauche".equals(item.getItemId())) {
				result = 15;
			}
			return result;
		}
		return 0;
	}

	public Mono<Integer> getFumble(CharacterItem item) {
		String feature = getFeature(item, CharacterItemFeatureType.SHIELD_BONUS);
		if (feature != null) {
			return Mono.just(Integer.parseInt(feature));
		}
		return weaponRepository.findById(item.getItemId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Weapon " + item.getId() + " no found")))
			.map(Weapon::getFumble);
	}

	public String getFeature(CharacterItem item, CharacterItemFeatureType type) {
		if (item.getFeatures() == null) {
			return null;
		}
		return item.getFeatures().stream()
			.filter(e -> e.getType() == type)
			.map(CharacterItemFeature::getValue)
			.findFirst().orElse(null);
	}

	public int getShieldBonus(String itemId) {
		switch (itemId) {
		case "target-shield", "normal-shield":
			return 20;
		case "full-shield":
			return 25;
		case "wall-shield":
			return 30;
		default:
			throw new DataConsistenceException("Unmapped shield " + itemId);
		}
	}

}
