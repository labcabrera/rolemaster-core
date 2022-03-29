package org.labcabrera.rolemaster.core.service.tactical;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TacticalNpcInstanceServiceTest {

	@Autowired
	private TacticalNpcInstanceService service;

	@Test
	void testNotFound() {
		assertThrows(BadRequestException.class, () -> service.create("not-existing-npc-id").share().block());
	}

}
