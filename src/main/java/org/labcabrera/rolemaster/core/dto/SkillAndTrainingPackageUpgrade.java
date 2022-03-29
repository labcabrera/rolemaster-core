package org.labcabrera.rolemaster.core.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillAndTrainingPackageUpgrade {

	@NotNull
	@Builder.Default
	private Map<String, Integer> categoryRanks = new LinkedHashMap<>();

	@NotNull
	@Builder.Default
	private Map<String, Integer> skillRanks = new LinkedHashMap<>();

	@Builder.Default
	private List<String> trainingPackages = new ArrayList<>();

}
