package org.labcabrera.rolemaster.core.services.rmss.character;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.TrainingPackageUpgrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@SpringBootTest
class CharacterAddTrainingPackageServiceImplValidatorTest {

	@Autowired
	private CharacterAddTrainingPackageServiceImpl service;

	@Test
	void testNullAuth() {
		JwtAuthenticationToken auth = null;
		String characterId = "test";
		TrainingPackageUpgrade request = mock(TrainingPackageUpgrade.class);
		assertThrows(ConstraintViolationException.class, () -> service.upgrade(auth, characterId, request));
	}

	@Test
	void testNullCharacterId() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		String characterId = null;
		TrainingPackageUpgrade request = mock(TrainingPackageUpgrade.class);
		assertThrows(ConstraintViolationException.class, () -> service.upgrade(auth, characterId, request));
	}

	@Test
	void testNullRequest() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		String characterId = "test";
		TrainingPackageUpgrade request = null;
		assertThrows(ConstraintViolationException.class, () -> service.upgrade(auth, characterId, request));
	}

}
