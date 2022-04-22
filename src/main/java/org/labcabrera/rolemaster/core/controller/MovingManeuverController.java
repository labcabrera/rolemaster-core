package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.table.maneuver.MovingManeuverResult;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Characters")
@RequestMapping("/moving-maneuvers")
public interface MovingManeuverController {

	//TODO REFACTORING MODEL
	Mono<MovingManeuverResult> execute(Object request);

}
