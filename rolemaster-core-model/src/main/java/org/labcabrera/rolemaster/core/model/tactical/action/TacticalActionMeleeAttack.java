package org.labcabrera.rolemaster.core.model.tactical.action;

import java.util.Map;

import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.validation.ValidTacticalActionMeleAttack;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Builder.Default
	private MeleeAttackMode meleeAttackMode = MeleeAttackMode.MAIN_WEAPON;

	@NotNull
	private MeleeAttackType meleeAttackType;

	@NotNull
	private Map<AttackTargetType, MeleeAttackFacing> facingMap;

	@Builder.Default
	private Integer parry = 0;

	@Schema(description = "Indicates whether the action has been used to stop the attack of a given attacker.")
	@Builder.Default
	private boolean parried = false;

	@Builder.Default
	private boolean blocked = false;

}
