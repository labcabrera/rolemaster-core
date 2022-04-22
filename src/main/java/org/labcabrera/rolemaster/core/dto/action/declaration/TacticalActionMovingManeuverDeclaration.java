package org.labcabrera.rolemaster.core.dto.action.declaration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TacticalActionMovingManeuverDeclaration extends TacticalActionDeclaration {

	private String skillId;

}
