package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSession;
import org.labcabrera.rolemaster.core.model.tactical.TemperatureMultiplier;
import org.labcabrera.rolemaster.core.model.tactical.TerrainType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class AttackExhaustionProcessorTest {

	@InjectMocks
	private AttackExhaustionProcessor processor;

	@Mock
	private TacticalSessionRepository sessionRepository;

	@Mock
	private TacticalRoundRepository roundRepository;

	@Mock
	private MeleeAttackContext context;

	@Mock
	private TacticalCharacter character;

	@Mock
	private TacticalSession tacticalSession;

	@Mock
	private TacticalRound tacticalRound;

	@Spy
	private TacticalActionMeleeAttack action;

	@BeforeEach
	void setUp() {
		action.setRoundId("round-01");
		when(tacticalRound.getTacticalSessionId()).thenReturn("session-01");

		when(sessionRepository.findById("session-01")).thenReturn(Mono.just(tacticalSession));
		when(roundRepository.findById("round-01")).thenReturn(Mono.just(tacticalRound));

		when(tacticalSession.getExhaustionMultiplier()).thenReturn(BigDecimal.ONE);
		when(tacticalSession.getTemperature()).thenReturn(TemperatureMultiplier.ABOVE_37);
		when(tacticalSession.getTerrain()).thenReturn(TerrainType.ROUGH);

		when(context.getAction()).thenReturn(action);
		when(context.isMeleeAttack()).thenReturn(true);
		when(context.getSource()).thenReturn(character);
		when(character.getHp()).thenReturn(Hp.builder().max(100).current(100).build());
	}

	@Test
	void test() {
		AttackContext<?> block = processor.apply(context).share().block();
		assertEquals(new BigDecimal("1.500"), block.getAction().getExhaustionPoints());
	}

	@Test
	void testHpM75() {
		when(character.getHp()).thenReturn(Hp.builder().max(100).current(70).build());
		AttackContext<?> block = processor.apply(context).share().block();
		assertEquals(new BigDecimal("2.000"), block.getAction().getExhaustionPoints());
	}

	@Test
	void testHpM50() {
		when(character.getHp()).thenReturn(Hp.builder().max(100).current(40).build());
		AttackContext<?> block = processor.apply(context).share().block();
		assertEquals(new BigDecimal("3.000"), block.getAction().getExhaustionPoints());
	}

	@Test
	void testMissile() {
		when(context.isMeleeAttack()).thenReturn(false);
		AttackContext<?> block = processor.apply(context).share().block();
		assertEquals(new BigDecimal("0.500"), block.getAction().getExhaustionPoints());
	}

}
