package org.labcabrera.rolemaster.core.service.maneuver.m.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovingManeuverServiceImplTest {

	@Autowired
	private MovingManeuverServiceImpl service;

	@Test
	void test() {
		MovingManeuverRequest request = MovingManeuverRequest.builder()
			.dificulty(ManeuverDificulty.EASY)
			.build();
		MovingManeuverResult result = service.apply(request);
		assertNotNull(result);
	}

}
