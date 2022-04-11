package org.labcabrera.rolemaster.core.dto.action.declaration;

import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackType;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonPropertyOrder({ "priority", "actionPercent", "source", "target", "meleeAttackType", "parry", "notes" })
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TacticalActionMeleeAttackDeclaration extends TacticalActionDeclaration {

	private String target;

	private MeleeAttackType meleeAttackType;

	private Integer parry;

}
