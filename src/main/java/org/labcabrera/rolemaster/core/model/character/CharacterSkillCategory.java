package org.labcabrera.rolemaster.core.model.character;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterSkillCategory {

	private String categoryId;

	@Builder.Default
	private List<AttributeType> attributes = new ArrayList<>();

	@Builder.Default
	private List<Integer> developmentCost = new ArrayList<>();

	@Builder.Default
	private Integer adolescenceRanks = 0;

	@Builder.Default
	private Integer upgradedRanks = 0;

	@Builder.Default
	private Map<BonusType, Integer> bonus = new LinkedHashMap<>();

	public Integer getTotalRanks() {
		return adolescenceRanks + upgradedRanks;
	}

	public Integer getTotalBonus() {
		return bonus.values().stream().reduce(0, (a, b) -> a + b);
	}

}
