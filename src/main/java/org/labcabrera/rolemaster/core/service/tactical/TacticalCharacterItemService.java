package org.labcabrera.rolemaster.core.service.tactical;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.item.ArmorItemType;
import org.labcabrera.rolemaster.core.model.item.ItemType;
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
		if (item.getType() == ItemType.ARMOR_PIECE && item.getArmorType() == ArmorItemType.SHIELD) {
			//TODO shield bonus
			return 20;
		}
		if (item.getType() == ItemType.WEAPON) {
			//TODO weapon bonus
			return 5;
		}
		return 0;
	}

	public Mono<Integer> getFumble(CharacterItem item) {
		//TODO check customization
		return weaponRepository.findById(item.getItemId())
			.switchIfEmpty(Mono.error(() -> new BadRequestException("Weapon " + item.getId() + " no found")))
			.map(e -> e.getFumble());
	}

}
