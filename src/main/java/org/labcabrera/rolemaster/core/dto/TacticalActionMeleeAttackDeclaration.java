package org.labcabrera.rolemaster.core.dto;

import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TacticalActionMeleeAttackDeclaration extends TacticalActionDeclaration {

	private MeleeAttackType meleeAttackType;

	private String target;

	private Integer parry;

}
