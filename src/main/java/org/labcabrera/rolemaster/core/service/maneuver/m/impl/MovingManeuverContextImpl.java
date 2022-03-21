package org.labcabrera.rolemaster.core.service.maneuver.m.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.service.maneuver.m.MovingManeuverContext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovingManeuverContextImpl implements MovingManeuverContext {

	private MovingManeuverRequest request;

	@Builder.Default
	private Optional<CharacterStatus> characterStatus = Optional.empty();

	@Builder.Default
	private Optional<CharacterInfo> characterInfo = Optional.empty();

	@Builder.Default
	private List<ManeuverModifier> maneuverModifiers = new ArrayList<>();

}
