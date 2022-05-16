package org.labcabrera.rolemaster.core.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingPackageSkillSelection {

	@NotEmpty
	private String key;

	@NotEmpty
	private String skillId;

	@NotNull
	private Integer ranks;

}
