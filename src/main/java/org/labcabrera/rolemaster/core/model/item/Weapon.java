package org.labcabrera.rolemaster.core.model.item;

import java.util.List;

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

	private String weaponType;

	private Integer fumble;

	private List<Integer> roture;

	private List<Integer> resistance;

	private List<RangeModifier> rangeModifiers;

}
