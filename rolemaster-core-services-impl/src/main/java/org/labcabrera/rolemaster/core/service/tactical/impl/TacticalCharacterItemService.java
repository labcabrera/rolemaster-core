package org.labcabrera.rolemaster.core.service.tactical.impl;

import java.util.List;

import org.labcabrera.rolemaster.core.dto.context.HasItemMap;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemFeature;
import org.labcabrera.rolemaster.core.model.character.item.ItemFeatureType;
import org.labcabrera.rolemaster.core.model.item.ArmorItemType;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.model.item.Weapon;
import org.labcabrera.rolemaster.core.model.item.WeaponRange;
import org.springframework.stereotype.Service;

@Service
public class TacticalCharacterItemService {

	private static final String MAINE_GAUCHE = "maine-gauche";

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
			if (MAINE_GAUCHE.equals(item.getItemId())) {
				result = 15;
			}
			return result;
		}
		return 0;
	}

	public Integer getFumble(CharacterItem item, HasItemMap hasItemMap) {
		String feature = getFeature(item, ItemFeatureType.SHIELD_BONUS);
		if (feature != null) {
			return Integer.parseInt(feature);
		}
		String itemId = item.getItemId();
		if (!hasItemMap.getItemMap().containsKey(itemId)) {
			throw new DataConsistenceException("Missing item " + itemId + ".");
		}
		Weapon weapon = hasItemMap.getItemMap().get(itemId).as(Weapon.class);
		return weapon.getFumble();
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

	public Integer getRangeModifier(CharacterItem item, Float distance, HasItemMap hasItemMap) {
		//TODO check customization & talents
		Weapon weapon = hasItemMap.getItemMap().get(item.getItemId()).as(Weapon.class);
		List<WeaponRange> list = weapon.getRangeModifiers().stream().filter(e -> checkDistance(e, distance)).toList();
		if (list.isEmpty()) {
			throw new BadRequestException("Invalid distance " + distance);
		}
		else if (list.size() > 1) {
			throw new DataConsistenceException("Multiple range modifiers for distance " + distance);
		}
		return list.iterator().next().getModifier();
	}

	public boolean isUnbreakable(CharacterItem item) {
		if (item.getFeatures() == null) {
			return false;
		}
		return item.getFeatures().stream().filter(e -> e.getType() == ItemFeatureType.UNBREAKABLE).count() > 0;
	}

	public Integer getBreakage(CharacterItem item, HasItemMap hasItemMap) {
		if (item.getFeatures() != null) {
			List<ItemFeature> list = item.getFeatures().stream().filter(e -> e.getType() == ItemFeatureType.BREAKAGE).toList();
			if (!list.isEmpty()) {
				return Integer.parseInt(list.iterator().next().getValue());
			}
		}
		Weapon weapon = hasItemMap.getItemMap().get(item.getItemId()).as(Weapon.class);
		return weapon.getBreakage();
	}

	public Integer getStrength(CharacterItem item, HasItemMap hasItemMap) {
		if (item.getFeatures() != null) {
			List<ItemFeature> list = item.getFeatures().stream().filter(e -> e.getType() == ItemFeatureType.BREAKAGE_STRENGTH).toList();
			if (!list.isEmpty()) {
				return Integer.parseInt(list.iterator().next().getValue());
			}
		}
		Weapon weapon = hasItemMap.getItemMap().get(item.getItemId()).as(Weapon.class);
		int min = weapon.getCommonStrength().getMin();
		int max = weapon.getCommonStrength().getMax();
		return (min + max) / 2;
	}

	private boolean checkDistance(WeaponRange range, Float distance) {
		return distance >= range.getMin() && distance <= range.getMax();
	}

}
