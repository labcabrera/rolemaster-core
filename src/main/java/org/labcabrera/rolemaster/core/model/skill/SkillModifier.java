package org.labcabrera.rolemaster.core.model.skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillModifier {

	private String key;

	private Integer modifier;

	private SkillAttributeModifier attributeModifier;

}
