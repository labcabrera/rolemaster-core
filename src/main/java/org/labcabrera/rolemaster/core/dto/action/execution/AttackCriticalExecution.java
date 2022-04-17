package org.labcabrera.rolemaster.core.dto.action.execution;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttackCriticalExecution {

	@Min(1)
	@Max(100)
	@NotNull
	private Integer roll;

}
