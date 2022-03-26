package org.labcabrera.rolemaster.core.model.character;

import java.util.LinkedHashMap;
import java.util.Map;

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
	private Integer totalBonus = 0;

	@Builder.Default
	private Map<AttributeBonusType, Integer> bonus = new LinkedHashMap<>();

	public Integer getTotalBonus() {
		return bonus.values().stream().reduce(0, (a, b) -> a + b);
	}

}
