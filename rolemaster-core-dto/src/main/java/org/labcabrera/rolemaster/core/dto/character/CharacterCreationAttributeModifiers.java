package org.labcabrera.rolemaster.core.dto.character;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.AttributeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterCreationAttributeModifiers {

	private Integer totalCost;

	private Map<AttributeType, CharacterCreationAttributeModifier> attributes;
}
