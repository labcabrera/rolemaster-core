package org.labcabrera.rolemaster.core.dto.action.execution;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FumbleExecution {

	@NotNull
	private Integer roll;

}
