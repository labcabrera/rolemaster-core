package org.labcabrera.rolemaster.core.dto.action.execution;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.tactical.actions.MeleeFlankType;

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
	private MeleeFlankType flankType = MeleeFlankType.NONE;

}
