package org.labcabrera.rolemaster.core.table.maneuver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaticManeuverResult {

	private String name;

	private Integer successPercent;

	private String text;

}
