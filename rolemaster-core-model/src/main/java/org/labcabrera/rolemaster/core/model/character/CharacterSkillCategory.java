package org.labcabrera.rolemaster.core.model.character;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.HasRanks;
import org.labcabrera.rolemaster.core.model.skill.SkillProgressionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterSkillCategory implements HasRanks {

	private String categoryId;

	private String group;

	@Builder.Default
	private List<AttributeType> attributes = new ArrayList<>();

	private SkillProgressionType progressionType;

	@Builder.Default
	private List<Integer> developmentCost = new ArrayList<>();

	@Builder.Default
	private Map<RankType, Integer> ranks = new EnumMap<>(RankType.class);

	@Builder.Default
	private Map<BonusType, Integer> bonus = new EnumMap<>(BonusType.class);

	public Integer getTotalRanks() {
		return ranks.values().stream().reduce(0, (a, b) -> a + b);
	}

	public Integer getTotalBonus() {
		return bonus.values().stream().reduce(0, (a, b) -> a + b);
	}

}
