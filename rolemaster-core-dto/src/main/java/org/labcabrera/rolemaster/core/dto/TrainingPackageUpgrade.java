package org.labcabrera.rolemaster.core.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TrainingPackageUpgrade {

	@NotEmpty
	private String trainingPackageId;

	@Builder.Default
	private List<TrainingPackageCategorySelection> categorySelection = new ArrayList<>();

	@Builder.Default
	private List<TrainingPackageSkillSelection> skillSelection = new ArrayList<>();

}
