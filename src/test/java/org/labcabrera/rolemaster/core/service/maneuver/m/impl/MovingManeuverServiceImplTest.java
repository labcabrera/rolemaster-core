package org.labcabrera.rolemaster.core.service.maneuver.m.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.MockCharacterConfigurer;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier;
import org.labcabrera.rolemaster.core.model.maneuver.ManeuverModifier.ModifierType;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverRequest;
import org.labcabrera.rolemaster.core.model.maneuver.m.MovingManeuverResult;
import org.labcabrera.rolemaster.core.model.session.Session;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacterContext;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterStatusRepository;
import org.labcabrera.rolemaster.core.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovingManeuverServiceImplTest {

	@Autowired
	private MovingManeuverServiceImpl service;

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	@Autowired
	private TacticalCharacterStatusRepository characterStatusRepository;

	private Session session;

	private CharacterInfo characterInfo;

	private TacticalCharacterContext characterStatus;

	void loadCharacterStatus() {
		session = Session.builder()
			.name("moving-maneuver-test")
			.build();
		session = sessionRepository.save(session).share().block();

		characterInfo = new CharacterInfo();
		MockCharacterConfigurer.configure(characterInfo);
		characterInfo = characterInfoRepository.save(characterInfo).share().block();

		characterStatus = TacticalCharacterContext.builder()
			.characterId(characterInfo.getId())
			.hp(50)
			.build();
		characterStatus = characterStatusRepository.save(characterStatus).share().block();
	}

	@Test
	void test() {
		MovingManeuverRequest request = MovingManeuverRequest.builder()
			.dificulty(ManeuverDificulty.EASY)
			.build();
		MovingManeuverResult result = service.apply(request).share().block();
		assertNotNull(result);
	}

	@Test
	void testWithStatus() {
		loadCharacterStatus();
		MovingManeuverRequest request = MovingManeuverRequest.builder()
			.characterStatusId(characterStatus.getId())
			.dificulty(ManeuverDificulty.EASY)
			.build();
		MovingManeuverResult result = service.apply(request).share().block();
		assertNotNull(result);

		Optional<ManeuverModifier> modifierHP = result.getModifiers().stream().filter(e -> e.getType() == ModifierType.HP).findFirst();
		assertTrue(modifierHP.isPresent());
		assertEquals(-10, modifierHP.get().getModifier());

	}

}
