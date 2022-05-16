package org.labcabrera.rolemaster.core.service.tactical.maneuvers.processor;

import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.labcabrera.rolemaster.core.dto.context.TacticalActionContext;
import org.labcabrera.rolemaster.core.model.tactical.Hp;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HpManeuverProcessorTest {

	@InjectMocks
	private HpManeuverProcessor processor;

	@Mock
	private TacticalActionContext<?> context;

	@Mock
	private TacticalCharacter tacticalCharacter;

	@Mock
	private Hp hp;

	@Mock
	private Map<String, Integer> bonusMap;

	@BeforeEach
	void setUp() {
		when(context.getSource()).thenReturn(tacticalCharacter);
		when(tacticalCharacter.getHp()).thenReturn(hp);
	}

	@ParameterizedTest
	@CsvSource({
		"100, 0",
		"75, 0",
		"74, -10",
		"50, -10",
		"49, -20",
		"25, -20",
		"24, -30",
	})
	void test100(int percent, int expectedBonus) {
		when(hp.getPercent()).thenReturn(percent);
		processor.loadBonus(context, bonusMap);
		Mockito.verify(bonusMap, Mockito.times(1)).put(HpManeuverProcessor.KEY, expectedBonus);
	}

}
