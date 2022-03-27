package org.labcabrera.rolemaster.core.service.maneuver.s.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.MockCharacterConfigurer;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.s.StaticManeuverResult;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StaticManeuverServiceImplTest {

	@Autowired
	private StaticManeuverServiceImpl service;

	@Autowired
	private CharacterInfoRepository characterRepository;

	@BeforeEach
	void setUp() {
		CharacterInfo ci = CharacterInfo.builder().build();
		MockCharacterConfigurer.configure(ci);
		characterRepository.save(ci);
	}

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
