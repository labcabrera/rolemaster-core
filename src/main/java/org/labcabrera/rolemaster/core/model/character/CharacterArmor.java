package org.labcabrera.rolemaster.core.model.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterArmor {

	@Builder.Default
	private Integer armor = 1;

	@Builder.Default
	private Integer armorDefensiveBonus = 0;

	@Builder.Default
	private Integer armorPenalty = 0;

	@Builder.Default
	private Integer armorQuPenalty = 0;

}
