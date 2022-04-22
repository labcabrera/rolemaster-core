package org.labcabrera.rolemaster.core.model.item;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class Weapon extends Item {

	private WeaponItemType weaponType;

	private Integer fumble;

	private List<Integer> roture;

	private List<Integer> resistance;

	private List<RangeModifier> rangeModifiers;

	@Schema(description = "Defensive bonus when the item is equipped as an off-hand item.", required = false)
	private Integer shieldBonus;

}
