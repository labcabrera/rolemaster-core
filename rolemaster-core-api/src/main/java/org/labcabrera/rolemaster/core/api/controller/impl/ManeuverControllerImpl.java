package org.labcabrera.rolemaster.core.api.controller.impl;

import org.labcabrera.rolemaster.core.api.controller.ManeuverController;
import org.labcabrera.rolemaster.core.dto.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.dto.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.MovingManeuverResult;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;
import org.labcabrera.rolemaster.core.table.maneuver.MovingManeuverService;
import org.labcabrera.rolemaster.core.table.maneuver.StaticManeuverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class ManeuverControllerImpl implements ManeuverController {

	@Autowired
	private MovingManeuverService movingManeuverService;

	@Autowired
	private StaticManeuverService staticManeuverService;

	@Override
	public Mono<MovingManeuverResult> executeMovingManeuver(MovingManeuverRequest request) {
		int total = getTotal(request);
		MovingManeuverResult result = movingManeuverService.getResult(request.getDifficulty(), total);
		return Mono.just(result);
	}

	@Override
	public Mono<StaticManeuverResult> executeStaticManeuver(StaticManeuverRequest request) {
		int total = getTotal(request);
		StaticManeuverResult result = staticManeuverService.getResult(total);
		return Mono.just(result);
	}

	private int getTotal(StaticManeuverRequest request) {
		int modifiers = request.getModifiers().values().stream().reduce(0, (a, b) -> a + b);
		return request.getRoll() + modifiers;
	}
}
