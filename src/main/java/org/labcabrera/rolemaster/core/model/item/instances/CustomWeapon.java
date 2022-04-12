package org.labcabrera.rolemaster.core.model.item.instances;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomWeapon {

	private String weaponCategoryId;

	private Integer bonus;

}
