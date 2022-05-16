package org.labcabrera.rolemaster.core.services.rmss.tactical;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.springframework.stereotype.Component;

@Component
public class TacticalCharacterItemResolver {

	public CharacterItem getMainHandWeapon(TacticalCharacter tc) {
		return getWeapon(tc, ItemPosition.MAIN_HAND);
	}

	public CharacterItem getOffHandWeapon(TacticalCharacter tc) {
		return getWeapon(tc, ItemPosition.OFF_HAND);
	}

	public CharacterItem getWeapon(TacticalCharacter tc, ItemPosition position) {
		return tc.getItems().stream()
			.filter(e -> e.getType() == ItemType.WEAPON)
			.filter(e -> e.getPosition() == position)
			.findFirst().orElseThrow(() -> new BadRequestException("Not valid weapon equiped in " + position));
	}

	public CharacterItem getWeapon(TacticalCharacter tc, AttackTargetType targetType) {
		ItemPosition position = targetType == AttackTargetType.MAIN_HAND ? ItemPosition.MAIN_HAND : ItemPosition.OFF_HAND;
		return tc.getItems().stream()
			.filter(e -> e.getType() == ItemType.WEAPON)
			.filter(e -> e.getPosition() == ItemPosition.MAIN_HAND)
			.findFirst().orElseThrow(() -> new BadRequestException("Not valid weapon equiped in " + position));
	}

	public CharacterItem getItem(TacticalCharacter tc, ItemPosition position) {
		return tc.getItems().stream()
			.filter(e -> e.getPosition() == position)
			.findFirst().orElse(null);
	}

}
