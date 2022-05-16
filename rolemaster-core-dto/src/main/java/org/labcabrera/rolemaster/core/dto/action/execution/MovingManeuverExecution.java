package org.labcabrera.rolemaster.core.dto.action.execution;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;

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
public class MovingManeuverExecution extends TacticalActionExecution {

	@NotNull
	private OpenRoll roll;

	private ManeuverDifficulty difficulty;

	@Builder.Default
	private Map<String, Integer> modifiers = new LinkedHashMap<>();

}
