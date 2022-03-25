package org.labcabrera.rolemaster.core.dto;

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
public class SkillUpgradeRequest {

	@Builder.Default
	private Map<String, Integer> categoryRanks = new LinkedHashMap<>();

	@Builder.Default
	private Map<String, Integer> skillRanks = new LinkedHashMap<>();

}
