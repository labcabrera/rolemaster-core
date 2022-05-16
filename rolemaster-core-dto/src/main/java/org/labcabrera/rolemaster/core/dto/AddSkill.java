package org.labcabrera.rolemaster.core.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddSkill {

	@NotEmpty
	@Schema(description = "Skill identifier.", required = true, example = "painting")
	private String skillId;

	@Schema(description = "Skill customizations.")
	private List<String> customizations;

}
