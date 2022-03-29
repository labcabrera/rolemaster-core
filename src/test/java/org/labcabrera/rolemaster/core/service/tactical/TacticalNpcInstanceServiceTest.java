package org.labcabrera.rolemaster.core.service.tactical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalNpcInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TacticalNpcInstanceServiceTest {

	@Autowired
	private TacticalNpcInstanceService service;

	@Test
	void testSuccess() {
		String npcId = "ork-figther-mele-i";
		TacticalNpcInstance npcInstance = service.create(npcId).share().block();
		assertNotNull(npcInstance);
		assertEquals(npcId, npcInstance.getNpcId());
		assertNotNull(npcInstance.getMetadata());
		assertNotNull(npcInstance.getName());
		assertNotNull(npcInstance.getMetadata().getCreated());
	}

	@Test
	void testUniqueName() {
		String npcId = "sauron";
		TacticalNpcInstance npcInstance = service.create(npcId).share().block();
		assertNotNull(npcInstance);
		assertEquals("Sauron", npcInstance.getName());
	}

	@Test
	void testNotFound() {
		assertThrows(BadRequestException.class, () -> service.create("not-existing-npc-id").share().block());
	}

}
