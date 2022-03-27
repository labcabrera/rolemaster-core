package org.labcabrera.rolemaster.core.model.character;

import java.util.ArrayList;
import java.util.EnumMap;
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
public class CharacterSkill {

	private String skillId;

	private String categoryId;

	@Builder.Default
	private List<Integer> developmentCost = new ArrayList<>();

	@Builder.Default
	private List<AttributeType> attributes = new ArrayList<>();

	@Builder.Default
	private Map<RankType, Integer> ranks = new EnumMap<>(RankType.class);

	@Builder.Default
	private Map<BonusType, Integer> bonus = new LinkedHashMap<>();

	public Integer getTotalRanks() {
		return ranks.values().stream().reduce(0, (a, b) -> a + b);
	}

	public Integer getTotalBonus() {
		return bonus.values().stream().reduce(0, (a, b) -> a + b);
	}

}
