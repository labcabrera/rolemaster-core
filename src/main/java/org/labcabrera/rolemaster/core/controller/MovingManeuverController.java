package org.labcabrera.rolemaster.core.controller;

import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverResult;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Characters")
@RequestMapping("/moving-maneuvers")
public interface MovingManeuverController {

	Mono<MovingManeuverResult> execute(MovingManeuverRequest request);

}
