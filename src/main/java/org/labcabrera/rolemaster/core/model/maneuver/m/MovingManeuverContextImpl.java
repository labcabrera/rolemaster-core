package org.labcabrera.rolemaster.core.model.maneuver.m;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.service.maneuver.m.MovingManeuverContext;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("movingManeuvers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovingManeuverContextImpl implements MovingManeuverContext {

	@Id
	private String id;

	private MovingManeuverRequest request;

	@Builder.Default
	private List<ManeuverModifier> maneuverModifiers = new ArrayList<>();

	@Transient
	@Builder.Default
	private Optional<TacticalCharacter> characterStatus = Optional.empty();

	@Transient
	@Builder.Default
	private Optional<CharacterInfo> characterInfo = Optional.empty();

}
