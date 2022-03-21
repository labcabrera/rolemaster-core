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

	private Integer adolescenseRanks;

	private Integer upgradedRanks;

	@Builder.Default
	private Integer defaultSkillBonus = 0;

	@Builder.Default
	private Integer totalBonus = 0;

}
