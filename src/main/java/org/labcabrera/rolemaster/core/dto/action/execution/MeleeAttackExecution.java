package org.labcabrera.rolemaster.core.dto.action.execution;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackFacing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MeleeAttackExecution extends TacticalActionExecution {

	private OpenRoll roll;

	@Schema(description = "Secondary roll when using two weapons.")
	private OpenRoll secondaryRoll;

	private String target;

	private String secondaryTarget;

	@Builder.Default
	private MeleeAttackFacing facing = MeleeAttackFacing.NORMAL;

	//TODO translate
	@Builder.Default
	private Boolean desenvainado = false;

}
