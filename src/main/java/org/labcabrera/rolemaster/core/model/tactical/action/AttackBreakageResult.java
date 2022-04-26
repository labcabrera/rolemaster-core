package org.labcabrera.rolemaster.core.model.tactical.action;

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
public class AttackBreakageResult {

	private Integer breakage;

	private Integer strength;

	private Integer roll;

	private Integer result;

	private Boolean weaponBreakage;

	@Builder.Default
	private Map<String, Integer> modifiers = new LinkedHashMap<>();

}
