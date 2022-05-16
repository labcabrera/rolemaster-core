package org.labcabrera.rolemaster.core.table.armor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.service.table.armor.ArmorService;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ArmorServiceTest {

	@InjectMocks
	private ArmorService service;

	@Spy
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws IOException {
		service.loadData();
	}

	@Test
	void test() {
		assertEquals(0, service.getArmorModifier(1).getMissilePenalty());
	}

}
