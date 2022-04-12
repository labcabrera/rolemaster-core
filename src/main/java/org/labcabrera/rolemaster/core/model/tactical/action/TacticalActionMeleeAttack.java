package org.labcabrera.rolemaster.core.model.tactical.action;

import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.validation.ValidTacticalActionMeleAttack;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ValidTacticalActionMeleAttack
@JsonPropertyOrder({ "type", "state", "priority", "actionPercent", "initiativeModifiers", "initiative", "source", "target",
	"meleAttackType", "parry", "notes" })
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TacticalActionMeleeAttack extends TacticalActionAttack {

	@NotNull
	private MeleeAttackType meleeAttackType;

	@Builder.Default
	private OffHandUsage offHandUsage = OffHandUsage.NONE;

	@Builder.Default
	private Integer parry = 0;

}
