package org.labcabrera.rolemaster.core.model.tactical;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
		Optional<CharacterWeapon> weapon = getWeapon(ItemPosition.MAIN_HAND);
		return weapon.isPresent() ? weapon.get() : getNoWeaponTemplate();
	}

	public Optional<CharacterWeapon> getOffHandWeapon() {
		return getWeapon(ItemPosition.OFF_HAND);
	}

	private Optional<CharacterWeapon> getWeapon(ItemPosition status) {
		CharacterItem item = items.stream().filter(e -> e.getPosition() == status).findFirst().orElse(null);
		if (item instanceof CharacterWeapon weapon) {
			return Optional.of(weapon);
		}
		return Optional.empty();
	}

	public Integer getEquipedArmor() {
		//TODO
		return 8;
	}

	@JsonIgnore
	private CharacterWeapon getNoWeaponTemplate() {
		//TODO
		return null;
	}
}
