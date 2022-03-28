package org.labcabrera.rolemaster.core.service.character.item;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterInventory;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.inventory.ItemStatus;
import org.labcabrera.rolemaster.core.repository.CharacterInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class CharacterItemService {

	@Autowired
	private CharacterInventoryRepository repository;

	public Mono<CharacterInventory> findByCharacterId(String characterId) {
		return repository.findByCharacterId(characterId);
	}

	public Mono<CharacterInventory> addItem(String caracterId, CharacterItem item) {
		return repository.findByCharacterId(caracterId)
			.map(e -> {
				item.setId(new ObjectId().toString());
				e.getItems().add(item);
				return e;
			})
			.flatMap(repository::save);
	}

	public Mono<Void> equip(String itemId) {
		return null;
	}

	public Mono<Void> unequip(String itemId) {
		return null;
	}

	public Mono<Void> store(String itemId) {
		return null;
	}

	public Mono<Void> equipWeapon(String itemId) {
		return null;
	}

	public Mono<Void> equipSecondaryWeapon(String itemId) {
		return null;
	}

	public Mono<Void> equipArmor(String itemId) {
		return null;
	}

	public Mono<Void> equipArmorAccesory(String itemId) {
		return null;
	}

	public BigDecimal getWeight(CharacterInventory inventory) {
		return inventory.getItems().stream()
			.filter(e -> e.getStatus() != null && e.getStatus() != ItemStatus.STORED && e.getWeigth() != null)
			.map(e -> e.getWeigth())
			.reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
	}

}
