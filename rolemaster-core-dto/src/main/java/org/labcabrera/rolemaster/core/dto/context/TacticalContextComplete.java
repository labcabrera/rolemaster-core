package org.labcabrera.rolemaster.core.dto.context;

import java.util.List;

import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TacticalContextComplete extends TacticalContext {

	private List<TacticalCharacter> characters;

	private List<TacticalAction> actions;

}
