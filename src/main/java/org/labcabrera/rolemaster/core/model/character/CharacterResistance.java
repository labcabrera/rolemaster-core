package org.labcabrera.rolemaster.core.model.character;

import java.util.EnumMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterResistance {

	@Builder.Default
	private Map<ResistanceBonusType, Integer> bonus = new EnumMap<>(ResistanceBonusType.class);

	public Integer getTotalBonus() {
		return bonus.values().stream().reduce(0, (a, b) -> a + b);
	}
}
