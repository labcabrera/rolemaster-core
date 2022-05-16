package org.labcabrera.rolemaster.core.service.character.item;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.item.ArmorItemType;
import org.labcabrera.rolemaster.core.model.item.ArmorPiece;
import org.labcabrera.rolemaster.core.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
class ArmorResolver {

	@Autowired
	private ItemRepository itemRepository;

	public <E> Mono<E> resolveArmor(E data, CharacterInfo characterInfo, List<CharacterItem> equipedItems) {
		List<CharacterItem> armorPieces = equipedItems.stream().filter(e -> e.getArmorType() == ArmorItemType.ARMOR).toList();
		if (armorPieces.isEmpty()) {
			characterInfo.getArmor().setArmor(1);
			return Mono.just(data);
		}
		else if (armorPieces.size() > 1) {
			throw new BadRequestException("Multiple armors equiped");
		}
		CharacterItem armor = armorPieces.iterator().next();
		return itemRepository.findById(armor.getItemId())
			.switchIfEmpty(Mono.error(() -> new DataConsistenceException("Missing item " + armor.getItemId())))
			.map(ArmorPiece.class::cast)
			.map(ap -> {
				characterInfo.getArmor().setArmor(ap.getArmor());
				return data;
			});
	}

}
