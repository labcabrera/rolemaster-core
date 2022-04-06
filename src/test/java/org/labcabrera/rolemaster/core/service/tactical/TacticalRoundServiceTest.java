package org.labcabrera.rolemaster.core.service.tactical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMovement;
import org.labcabrera.rolemaster.core.service.RandomService;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TacticalRoundServiceTest {

	@InjectMocks
	private TacticalRoundService service;

	@Spy
	private RandomService randomService;

	@Test
	void test() {
		TacticalRound round = TacticalRound.builder().build();
		round.getActions().add(TacticalActionMovement.builder().source("s-01").build());
		round.getActions().add(TacticalActionMovement.builder().source("s-02").build());
		round.getActions().add(TacticalActionMovement.builder().source("s-03").build());
		round.getActions().add(TacticalActionMovement.builder().source("s-01").build());
		Set<String> set = service.getCharacters(round);
		assertEquals(3, set.size());
	}

	@Test
	void testRandomInitiative() {
		TacticalRound round = TacticalRound.builder().build();
		round.getActions().add(TacticalActionMovement.builder().source("s-01").build());
		round.getActions().add(TacticalActionMovement.builder().source("s-02").build());
		round.getActions().add(TacticalActionMovement.builder().source("s-03").build());
		round.getInitiativeRollMap().put("s-01", 30);
		service.generateRandomInitiatives(round);
		assertTrue(round.getInitiativeRollMap().containsKey("s-01"));
		assertTrue(round.getInitiativeRollMap().containsKey("s-02"));
		assertTrue(round.getInitiativeRollMap().containsKey("s-03"));
		assertEquals(30, round.getInitiativeRollMap().get("s-01"));

	}

}
