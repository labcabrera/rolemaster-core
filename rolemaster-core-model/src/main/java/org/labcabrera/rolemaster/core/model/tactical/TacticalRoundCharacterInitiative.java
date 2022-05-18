package org.labcabrera.rolemaster.core.model.tactical;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalRoundCharacterInitiative {

	@Builder.Default
	private Map<String, Integer> bonus = new LinkedHashMap<>();

}
