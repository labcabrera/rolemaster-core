package org.labcabrera.rolemaster.core.dto.action.execution;

import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;

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
public class MovementExecution extends TacticalActionExecution {

	@NotNull
	private ManeuverDifficulty difficulty;

	private Integer customModifier;

	private Integer roll;

}
