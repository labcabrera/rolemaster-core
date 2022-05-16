package org.labcabrera.rolemaster.core.model.item;

import java.util.List;

import org.labcabrera.rolemaster.core.model.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Weapon extends Item {

	private WeaponItemType weaponType;

	private Integer fumble;

	private Integer breakage;

	private Range<Integer> commonStrength;

	private List<WeaponRange> rangeModifiers;

	private String projectileItemId;

}
