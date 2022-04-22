package org.labcabrera.rolemaster.core.dto.action.declaration;

import java.util.EnumMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackType;
import org.labcabrera.rolemaster.core.validation.ValidTacticalActionMeleAttackDeclaration;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ValidTacticalActionMeleAttackDeclaration
@JsonPropertyOrder({ "priority", "actionPercent", "source", "target", "meleeAttackType", "parry", "notes" })
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TacticalActionMeleeAttackDeclaration extends TacticalActionDeclaration {

	@Builder.Default
	private Map<AttackTargetType, String> targets = new EnumMap<>(AttackTargetType.class);

	private MeleeAttackMode meleeAttackMode;

	private MeleeAttackType meleeAttackType;

	private Integer parry;

}
