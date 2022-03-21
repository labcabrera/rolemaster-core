package org.labcabrera.rolemaster.core.service.maneuver.m.impl;

import java.util.List;
import java.util.Optional;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.status.CharacterStatus;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverResult;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.CharacterStatusRepository;
import org.labcabrera.rolemaster.core.service.maneuver.m.MovingManeuverContext;
import org.labcabrera.rolemaster.core.service.maneuver.m.MovingManeuverModifierProcessor;
import org.labcabrera.rolemaster.core.service.maneuver.m.MovingManeuverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovingManeuverServiceImpl implements MovingManeuverService {

	@Autowired
	private List<MovingManeuverModifierProcessor> modifierProcessors;

	@Autowired
	private CharacterStatusRepository characterStatusRepository;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	@Override
	public MovingManeuverResult apply(MovingManeuverRequest request) {
		MovingManeuverContext context = MovingManeuverContextImpl.builder()
			.request(request)
			.maneuverModifiers(request.getCustomModifiers())
			.build();

		//TODO non-reactive
		if (request.getCharacterStatusId() != null) {
			CharacterStatus status = characterStatusRepository.findById(request.getCharacterStatusId()).share().block();
			context.setCharacterStatus(Optional.of(status));

			CharacterInfo characterInfo = characterInfoRepository.findById(status.getCharacterId()).share().block();
			context.setCharacterInfo(Optional.of(characterInfo));
		}

		modifierProcessors.stream().forEach(processor -> processor.accept(context));

		MovingManeuverResult result = MovingManeuverResult.builder()
			.modifiers(context.getManeuverModifiers())
			.build();

		return result;
	}

}
