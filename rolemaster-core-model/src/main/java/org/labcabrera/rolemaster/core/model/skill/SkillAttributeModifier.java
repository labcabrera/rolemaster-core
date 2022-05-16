package org.labcabrera.rolemaster.core.model.skill;

import org.labcabrera.rolemaster.core.model.character.AttributeType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillAttributeModifier {

	private AttributeType attributeType;

	private Integer multiplier;
}
