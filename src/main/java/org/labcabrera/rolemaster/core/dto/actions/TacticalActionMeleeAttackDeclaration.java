package org.labcabrera.rolemaster.core.dto.actions;

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

	private MeleeAttackType meleeAttackType;

	private String target;

	private Integer parry;

}