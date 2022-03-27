package org.labcabrera.rolemaster.core.service.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.combat.Attack;
import org.labcabrera.rolemaster.core.model.combat.AttackState;
import org.labcabrera.rolemaster.core.repository.AttackRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class CombatServiceTest {

	@InjectMocks
	private CombatService combatService;

	@Mock
	private AttackRepository attackRepository;

	@Spy
	private Attack attackRequest;

	@BeforeEach
	void prepare() {
		lenient().when(attackRepository.save(attackRequest)).thenReturn(Mono.just(attackRequest));
		lenient().when(attackRepository.findById("a-01")).thenReturn(Mono.just(attackRequest));
	}

	@Test
	void test() {
		attackRequest.setTacticalSession("tc-01");
		attackRequest.setAttackerId("a-01");
		attackRequest.setDefenderId("d-02");

		Attack result = combatService.prepare(attackRequest).share().block();

		assertNotNull(result);
		assertEquals(AttackState.UNRESOLVED, result.getState());

		OpenRoll roll = OpenRoll.builder().result(55).build();

		Attack resolved = combatService.resolve("a-01", roll).share().block();

		assertNotNull(resolved);
		assertEquals(AttackState.RESOLVED, result.getState());
	}

}
