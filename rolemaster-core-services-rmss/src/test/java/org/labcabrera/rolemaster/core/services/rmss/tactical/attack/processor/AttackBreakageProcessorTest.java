package org.labcabrera.rolemaster.core.services.rmss.tactical.attack.processor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AttackBreakageProcessorTest {

	@InjectMocks
	private AttackBreakageProcessor processor;

	@Test
	void test01() {
		assertFalse(processor.checkBreakage(1, OpenRoll.of(12)));
	}

	@Test
	void test02() {
		assertTrue(processor.checkBreakage(1, OpenRoll.of(11)));
	}

	@Test
	void test03() {
		assertFalse(processor.checkBreakage(1, OpenRoll.of(22)));
	}

}
