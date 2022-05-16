package org.labcabrera.rolemaster.core.services.rmss.character.creation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.services.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@SpringBootTest
class CharacterCreationServiceImplValidationTest {

	@Autowired
	private CharacterCreationService service;

	@Test
	void testNullAuth() {
		JwtAuthenticationToken auth = null;
		CharacterCreation request = mock(CharacterCreation.class);
		assertThrows(ConstraintViolationException.class, () -> service.create(auth, request));
	}

	@Test
	void testNullRequest() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		CharacterCreation request = null;
		assertThrows(ConstraintViolationException.class, () -> service.create(auth, request));
	}

	@Test
	void testInvalidRequest() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		CharacterCreation request = mock(CharacterCreation.class);
		assertThrows(ConstraintViolationException.class, () -> service.create(auth, request));
	}

}
