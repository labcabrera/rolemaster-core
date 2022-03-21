package org.labcabrera.rolemaster.core.model.character;

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
	private Integer currentRank = 0;

	@Builder.Default
	private Integer adolescenseRank = 0;

}
