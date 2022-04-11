package org.labcabrera.rolemaster.core.model.tactical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContextCharacterAttack {

	private String mainWeaponEquippedId;

	private Integer mainWeaponBonus;

	private String secondaryWeaponEquippedId;

	private Integer secondaryWeaponBonus;

}
