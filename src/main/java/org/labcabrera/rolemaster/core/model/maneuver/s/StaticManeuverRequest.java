package org.labcabrera.rolemaster.core.model.maneuver.s;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaticManeuverRequest {

	private Integer roll;

	private ManeuverDificulty dificulty;

	private String characterStatusId;

}
