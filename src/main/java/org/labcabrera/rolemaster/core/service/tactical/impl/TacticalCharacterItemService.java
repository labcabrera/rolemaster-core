package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.util.List;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemFeature;
import org.labcabrera.rolemaster.core.model.character.item.ItemFeatureType;
import org.labcabrera.rolemaster.core.model.item.ArmorItemType;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.labcabrera.rolemaster.core.model.item.WeaponRange;
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
		String feature = getFeature(item, ItemFeatureType.SHIELD_BONUS);
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
		String feature = getFeature(item, ItemFeatureType.SHIELD_BONUS);
		if (feature != null) {
			return Mono.just(Integer.parseInt(feature));
		}
		return weaponRepository.findById(item.getItemId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Weapon " + item.getId() + " no found")))
			.map(Weapon::getFumble);
	}

	public String getFeature(CharacterItem item, ItemFeatureType type) {
		if (item.getFeatures() == null) {
			return null;
		}
		return item.getFeatures().stream()
			.filter(e -> e.getType() == type)
			.map(ItemFeature::getValue)
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

	public Mono<Integer> getRangeModifier(CharacterItem item, Float distance) {
		//TODO check customization & talents
		return weaponRepository.findById(item.getItemId())
			.map(weapon -> {
				List<WeaponRange> list = weapon.getRangeModifiers().stream().filter(e -> checkDistance(e, distance)).toList();
				if (list.size() == 0) {
					throw new BadRequestException("Invalid distance " + distance);
				}
				else if (list.size() > 1) {
					throw new DataConsistenceException("Multiple range modifiers for distance " + distance);
				}
				return list.iterator().next().getModifier();
			});
	}

	public boolean isUnbreakable(CharacterItem item) {
		if (item.getFeatures() == null) {
			return false;
		}
		return item.getFeatures().stream().filter(e -> e.getType() == ItemFeatureType.UNBREAKABLE).count() > 0;
	}

	public Mono<Integer> getBreakage(CharacterItem item) {
		if (item.getFeatures() != null) {
			List<ItemFeature> list = item.getFeatures().stream().filter(e -> e.getType() == ItemFeatureType.BREAKAGE).toList();
			if (!list.isEmpty()) {
				return Mono.just(Integer.parseInt(list.iterator().next().getValue()));
			}
		}
		return weaponRepository.findById(item.getItemId()).map(Weapon::getBreakage);
	}

	public Mono<Integer> getStrength(CharacterItem item) {
		if (item.getFeatures() != null) {
			List<ItemFeature> list = item.getFeatures().stream().filter(e -> e.getType() == ItemFeatureType.BREAKAGE_STRENGTH).toList();
			if (!list.isEmpty()) {
				return Mono.just(Integer.parseInt(list.iterator().next().getValue()));
			}
		}
		return weaponRepository.findById(item.getItemId())
			.map(weapon -> {
				int min = weapon.getCommonStrength().getMin();
				int max = weapon.getCommonStrength().getMax();
				return (min + max) / 2;
			});
	}

	private boolean checkDistance(WeaponRange range, Float distance) {
		return distance >= range.getMin() && distance <= range.getMax();
	}

}
