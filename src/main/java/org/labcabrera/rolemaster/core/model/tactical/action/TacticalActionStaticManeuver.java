package org.labcabrera.rolemaster.core.model.tactical.action;

import java.util.LinkedHashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.table.maneuver.StaticManeuverResult;

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
public class TacticalActionStaticManeuver extends TacticalAction {

	private String skillId;

	@Builder.Default
	private Map<String, Integer> modifiers = new LinkedHashMap<>();

	private ManeuverDificulty dificulty;

	private OpenRoll roll;

	private StaticManeuverResult result;

}
