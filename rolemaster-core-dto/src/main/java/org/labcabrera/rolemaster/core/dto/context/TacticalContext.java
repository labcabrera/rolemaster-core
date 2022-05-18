package org.labcabrera.rolemaster.core.dto.context;

import java.util.List;

import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.strategic.StrategicSession;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TacticalContext {

	protected StrategicSession strategicSession;

	protected TacticalSession tacticalSession;

	protected TacticalRound tacticalRound;

	protected List<TacticalCharacter> characters;

	protected List<TacticalAction> roundActions;

	public TacticalCharacter getCharacter(String id) {
		return characters.stream()
			.filter(e -> e.getId().equals(id))
			.findFirst().orElseThrow(() -> new DataConsistenceException(String.format("Character %s not found.", id)));
	}

}
