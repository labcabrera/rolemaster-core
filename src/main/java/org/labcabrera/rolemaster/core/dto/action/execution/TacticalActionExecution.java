package org.labcabrera.rolemaster.core.dto.action.execution;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class TacticalActionExecution {

	@NotBlank
	private String actionId;

}
