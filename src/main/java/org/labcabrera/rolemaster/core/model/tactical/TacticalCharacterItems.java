package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.item.instances.CustomWeapon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalCharacterItems {

	private CustomWeapon mainWeapon;

	private CustomWeapon offhandWeapon;

	private String mainWeaponId;

	private Integer mainWeaponBonus;

	private String secondaryWeaponId;

	private Integer secondaryWeaponBonus;

}
