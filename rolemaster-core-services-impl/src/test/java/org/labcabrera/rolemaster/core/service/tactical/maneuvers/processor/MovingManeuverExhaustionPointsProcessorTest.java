package org.labcabrera.rolemaster.core.service.tactical.maneuvers.processor;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.model.tactical.ExhaustionPoints;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.service.tactical.processor.ExhaustionBonusProcessor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovingManeuverExhaustionPointsProcessorTest {

	@InjectMocks
	private MovingManeuverExhaustionPointsProcessor processor;

	@Spy
	private ExhaustionBonusProcessor exhaustionBonusProcessor;

	@Mock
	private TacticalActionContext<?> context;

	@Mock
	private TacticalCharacter tacticalCharacter;

	@Mock
	private ExhaustionPoints exhaustionPoints;

	@Mock
	private Map<String, Integer> bonusMap;

	@BeforeEach
	void setUp() {
		when(context.getSource()).thenReturn(tacticalCharacter);
		when(tacticalCharacter.getExhaustionPoints()).thenReturn(exhaustionPoints);
	}

	@ParameterizedTest
	@CsvSource({
		"100, 0",
		"75, 0",
		"74, -5",
		"50, -5",
		"49, -15",
		"10, -30",
		"1, -60",
		"0, -100",
	})
	void test(int percent, int expectedBonus) {
		when(exhaustionPoints.getPercent()).thenReturn(percent);
		processor.loadBonus(context, bonusMap);
		verify(bonusMap, times(1)).put(MovingManeuverExhaustionPointsProcessor.KEY, expectedBonus);
	}

}
