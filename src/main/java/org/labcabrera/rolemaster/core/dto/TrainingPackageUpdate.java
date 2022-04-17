package org.labcabrera.rolemaster.core.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingPackageUpdate {

	@NotEmpty
	private String trainingPackageId;

}
