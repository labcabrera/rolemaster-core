package org.labcabrera.rolemaster.core.model.tactical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContextCharacterItems {

	private String mainWeaponEquipedId;

	private String secondaryWeaponEquipedId;

	private String armorEquipedId;

}
