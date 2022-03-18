package org.labcabrera.rolemaster.core.model.smaneuver;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaticManeuverContext {

	private StaticManeuverRequest request;

	private CharacterInfo characterInfo;

	private List<StaticManeuverModifier> modifiers;

}
