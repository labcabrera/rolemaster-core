package org.labcabrera.rolemaster.core.model.maneuver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManeuverModifier {

	private Integer modifier;

	private String description;

	private ModifierType type;

	public enum ModifierType {
		DIFICULTY, HP
	}

}
