package org.labcabrera.rolemaster.core.service.character.creation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AttributeCreationServiceTest {

	@InjectMocks
	private AttributeCreationServiceImpl service;

	@Test
	void test() {
		assertEquals(190, service.getCost(100));
		assertEquals(171, service.getCost(99));
		assertEquals(154, service.getCost(98));
		assertEquals(139, service.getCost(97));
		assertEquals(126, service.getCost(96));
		assertEquals(115, service.getCost(95));
		assertEquals(106, service.getCost(94));
		assertEquals(99, service.getCost(93));
		assertEquals(94, service.getCost(92));
		assertEquals(91, service.getCost(91));
		assertEquals(90, service.getCost(90));
	}

}
