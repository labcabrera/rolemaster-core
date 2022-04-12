package org.labcabrera.rolemaster.core.model.tactical;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.character.inventory.ItemStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalCharacterInventory {

	@Builder.Default
	@Schema(description = "Equipment carried by the character.", required = true)
	private List<CharacterItem> items = new ArrayList<>();

	public CharacterWeapon getMainHandWeapon() {
		Optional<CharacterWeapon> weapon = getWeapon(ItemStatus.MAIN_HAND);
		return weapon.isPresent() ? weapon.get() : getNoWeaponTemplate();
	}

	public Optional<CharacterWeapon> getOffHandWeapon() {
		return getWeapon(ItemStatus.OFF_HAND);
	}

	private Optional<CharacterWeapon> getWeapon(ItemStatus status) {
		CharacterItem item = items.stream().filter(e -> e.getStatus() == status).findFirst().orElse(null);
		if (item == null || !(item instanceof CharacterWeapon)) {
			return Optional.empty();
		}
		else {
			return Optional.of((CharacterWeapon) item);
		}
	}

	public Integer getEquipedArmor() {
		//TODO
		return 8;
	}

	private CharacterWeapon getNoWeaponTemplate() {
		throw new NotImplementedException();
		//return CharacterWeapon.builder().build();
	}
}
