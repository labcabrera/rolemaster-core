package org.labcabrera.rolemaster.core.dto.character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterCreationAttributeModifier {

	private Integer currentValue;

	private Integer attributeBonus;

	private Integer cost;

}
