package org.labcabrera.rolemaster.core.service.maneuver.s.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StaticManeuverServiceImplTest {

	@Autowired
	private StaticManeuverServiceImpl service;

	@Test
	void test() {
		StaticManeuverRequest request = StaticManeuverRequest.builder()
			.dificulty(ManeuverDificulty.EASY)
			.build();

		StaticManeuverResult result = service.apply(request);

		assertNotNull(result);
		assertNotNull(result.getModifiers());
	}

}
