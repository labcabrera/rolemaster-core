package org.labcabrera.rolemaster.core.model.character;

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
	private Integer adolescenceRanks = 0;

	@Builder.Default
	private Integer upgradedRanks = 0;

	@Builder.Default
	private Integer attributeBonus = 0;

	@Builder.Default
	private Integer defaultSkillBonus = 0;

	@Builder.Default
	private Integer totalBonus = 0;

}
