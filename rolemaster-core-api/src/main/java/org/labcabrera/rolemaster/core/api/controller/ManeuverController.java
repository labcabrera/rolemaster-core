package org.labcabrera.rolemaster.core.api.controller;

import org.labcabrera.rolemaster.core.dto.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.dto.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.MovingManeuverResult;
import org.labcabrera.rolemaster.core.model.maneuver.StaticManeuverResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Maneuvers")
@RequestMapping("/maneuvers")
public interface ManeuverController {

	@PostMapping("/moving")
	@Operation(summary = "Get the result of a moving maneuver roll.")
	Mono<MovingManeuverResult> executeMovingManeuver(@RequestBody MovingManeuverRequest request);

	@PostMapping("/static")
	@Operation(summary = "Get the result of a static maneuver roll.")
	Mono<StaticManeuverResult> executeStaticManeuver(@RequestBody StaticManeuverRequest request);

}
