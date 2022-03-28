package org.labcabrera.rolemaster.core.model.tactical.actions;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.ActionPriority;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class TacticalAction {

	@Id
	private String id;

	private String characterId;

	private TacticalActionState state;

	private ActionPriority phase;

	@Min(1)
	@Max(100)
	private Integer actionPercent;

}
