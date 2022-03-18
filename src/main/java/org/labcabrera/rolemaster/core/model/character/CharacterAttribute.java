package org.labcabrera.rolemaster.core.model.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterAttribute {

	private Integer currentValue;

	private Integer potentialValue;

	private Integer baseBonus;

	private Integer racialBonus;

	private Integer spetialBonus;

}
