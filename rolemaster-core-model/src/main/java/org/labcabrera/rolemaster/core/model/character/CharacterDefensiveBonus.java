package org.labcabrera.rolemaster.core.model.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterDefensiveBonus {

	private Integer attributeBonus;

	private Integer armorQuicknessPenalty;

	private Integer shieldBonus;

	private Integer specialBonus;	

	private Integer totalBonus;

}
