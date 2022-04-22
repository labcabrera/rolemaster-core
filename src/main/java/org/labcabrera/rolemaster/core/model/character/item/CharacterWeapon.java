package org.labcabrera.rolemaster.core.model.character.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CharacterWeapon extends CharacterItem {

	private String skillId;

	private String weaponTableId;

	private Integer fumble;

}
