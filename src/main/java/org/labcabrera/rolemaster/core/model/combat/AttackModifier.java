package org.labcabrera.rolemaster.core.model.combat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttackModifier {

	private String description;

	private Integer modifier;

}
