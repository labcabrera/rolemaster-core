package org.labcabrera.rolemaster.core.model.tactical.action;

import java.util.LinkedHashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDifficulty;
import org.labcabrera.rolemaster.core.model.maneuver.MovingManeuverResult;

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
public class TacticalActionMovement extends TacticalAction {

	@Builder.Default
	private MovementPace pace = MovementPace.WALK;

	private ManeuverDifficulty difficulty;

	@Builder.Default
	private Map<String, Integer> bonusMap = new LinkedHashMap<>();

	private MovingManeuverResult maneuverResult;

	private Integer roll;

	private Double distance;

	private Double distanceScaled;

}
