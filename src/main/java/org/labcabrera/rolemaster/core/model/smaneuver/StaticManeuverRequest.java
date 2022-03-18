package org.labcabrera.rolemaster.core.model.smaneuver;

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

	private StaticManeuverDificulty dificulty;

}
