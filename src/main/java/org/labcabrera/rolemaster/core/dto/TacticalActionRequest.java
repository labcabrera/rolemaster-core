package org.labcabrera.rolemaster.core.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class TacticalActionRequest {

	@NotNull
	private String source;

	@NotNull
	private TacticalActionPhase priority;

	@NotNull
	@Min(1)
	@Max(100)
	private Integer actionPercent;

}
