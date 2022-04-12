package org.labcabrera.rolemaster.core.dto.action.execution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttackCriticalExecution {

	private String actionId;

	private Integer roll;

}
