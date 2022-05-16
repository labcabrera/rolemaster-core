package org.labcabrera.rolemaster.core.service.context;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalActionContext<E extends TacticalAction> {

	private E action;

	private TacticalSession tacticalSession;

	private TacticalRound tacticalRound;

	private TacticalCharacter source;

}
