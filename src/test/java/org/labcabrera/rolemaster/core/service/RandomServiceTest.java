package org.labcabrera.rolemaster.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.OpenRoll;

class RandomServiceTest {

	private RandomService service = new RandomService();

	@Test
	void testD100() {
		for (int i = 0; i < 1000; i++) {
			int value = service.d100();
			assertTrue(value > 0);
			assertTrue(value < 101);
		}
	}

	@Test
	void testD100Open() {
		for (int i = 0; i < 1000; i++) {
			OpenRoll value = service.d100FullOpen();
			assertNotNull(value);
		}
	}

}
