package org.labcabrera.rolemaster.core.model.character;

import java.util.ArrayList;
import java.util.List;

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

	@Builder.Default
	private List<Integer> developmentCost = new ArrayList<>();

	@Builder.Default
	private Integer adolescenceRanks = 0;

	@Builder.Default
	private Integer upgradedRanks = 0;

	@Builder.Default
	private Integer totalRanks = 0;

	@Builder.Default
	private Integer attributeBonus = 0;

	@Builder.Default
	private Integer defaultSkillBonus = 0;

	@Builder.Default
	private Integer rankBonus = 0;

	@Builder.Default
	private Integer professionBonus = 0;

	@Builder.Default
	private Integer specialBonus = 0;

	@Builder.Default
	private Integer totalBonus = 0;

}
