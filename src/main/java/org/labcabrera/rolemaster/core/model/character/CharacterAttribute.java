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

	@Builder.Default
	private Integer currentValue = 0;

	@Builder.Default
	private Integer potentialValue = 0;

	@Builder.Default
	private Integer baseBonus = 0;

	@Builder.Default
	private Integer racialBonus = 0;

	@Builder.Default
	private Integer specialBonus = 0;

	public Integer getTotalBonus() {
		return baseBonus + racialBonus + specialBonus;
	}

}
