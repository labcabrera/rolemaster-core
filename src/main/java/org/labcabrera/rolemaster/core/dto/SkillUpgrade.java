package org.labcabrera.rolemaster.core.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.validation.ValidSkillUpgrade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ValidSkillUpgrade
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillUpgrade {

	@NotNull
	@Builder.Default
	private Map<String, Integer> categoryRanks = new LinkedHashMap<>();

	@NotNull
	@Builder.Default
	private Map<String, Integer> skillRanks = new LinkedHashMap<>();

}
