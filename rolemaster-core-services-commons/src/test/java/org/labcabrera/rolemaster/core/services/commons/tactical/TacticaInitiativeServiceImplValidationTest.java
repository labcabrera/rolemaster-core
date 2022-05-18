package org.labcabrera.rolemaster.core.services.commons.tactical;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.tactical.InitiativeDeclaration;
import org.labcabrera.rolemaster.core.dto.tactical.TacticalCharacterInitiativeDeclaration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@SpringBootTest
class TacticaInitiativeServiceImplValidationTest {

	@Autowired
	private TacticaInitiativeServiceImpl service;

	@Test
	void testNullAuth() {
		String tacticalSessionID = "mock";
		InitiativeDeclaration declaration = mock(InitiativeDeclaration.class);
		assertThrows(ConstraintViolationException.class, () -> service.initiativeDeclaration(null, tacticalSessionID, declaration));
	}

	@Test
	void testNullSessionId() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		InitiativeDeclaration declaration = mock(InitiativeDeclaration.class);
		assertThrows(ConstraintViolationException.class, () -> service.initiativeDeclaration(auth, null, declaration));
	}

	@Test
	void testNullDeclaration() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		String tacticalSessionID = "mock";
		assertThrows(ConstraintViolationException.class, () -> service.initiativeDeclaration(auth, tacticalSessionID, null));
	}

	@Test
	void testInvalidDeclarationEmpty() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		String tacticalSessionID = "mock";
		InitiativeDeclaration declaration = InitiativeDeclaration.builder().build();
		assertThrows(ConstraintViolationException.class, () -> service.initiativeDeclaration(auth, tacticalSessionID, declaration));
	}

	@Test
	void testInvalidDeclarationEmptyRoll() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		String tacticalSessionID = "mock";
		InitiativeDeclaration declaration = InitiativeDeclaration.builder().build();
		declaration.getCharacters().add(TacticalCharacterInitiativeDeclaration.builder()
			.characterId("character-01")
			.build());
		assertThrows(ConstraintViolationException.class, () -> service.initiativeDeclaration(auth, tacticalSessionID, declaration));
	}

	@Test
	void testInvalidDeclarationEmptyCharacterId() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		String tacticalSessionID = "mock";
		InitiativeDeclaration declaration = InitiativeDeclaration.builder().build();
		declaration.getCharacters().add(TacticalCharacterInitiativeDeclaration.builder()
			.initiativeRoll(12)
			.build());
		assertThrows(ConstraintViolationException.class, () -> service.initiativeDeclaration(auth, tacticalSessionID, declaration));
	}

}
