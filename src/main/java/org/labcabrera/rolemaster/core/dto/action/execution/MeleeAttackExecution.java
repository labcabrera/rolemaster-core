package org.labcabrera.rolemaster.core.dto.action.execution;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeAttackPosition;

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

	private String target;

	@Builder.Default
	private MeleeAttackPosition position = MeleeAttackPosition.NORMAL;

	//TODO translate
	@Builder.Default
	private Boolean desenvainado = false;

}
