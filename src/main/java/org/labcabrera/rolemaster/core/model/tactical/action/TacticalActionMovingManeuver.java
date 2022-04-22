package org.labcabrera.rolemaster.core.model.tactical.action;

import java.util.HashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.table.maneuver.MovingManeuverResult;

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
public class TacticalActionMovingManeuver extends TacticalAction {

	private String skillId;

	@Builder.Default
	private Map<String, Integer> modifiers = new HashMap<>();

	private OpenRoll roll;

	private MovingManeuverResult result;

}
