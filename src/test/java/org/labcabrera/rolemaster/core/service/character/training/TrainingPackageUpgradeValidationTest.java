package org.labcabrera.rolemaster.core.service.character.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.MockAuthentication;
import org.labcabrera.rolemaster.core.dto.TrainingPackageUpgrade;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.service.character.TrainingPackageUpgradeService;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@SpringBootTest
class TrainingPackageUpgradeValidationTest {

	@Autowired
	private CharacterCreationService service;

	@Autowired
	private TrainingPackageUpgradeService traningPackageUpgradeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testMissingTrainingPackageId() throws IOException {
		JwtAuthenticationToken auth = MockAuthentication.mock();
		CharacterCreation request = readRequest();
		CharacterInfo character = service.create(auth, request).share().block();
		assertEquals(61, character.getDevelopmentPoints().getTotalPoints());
		assertEquals(0, character.getDevelopmentPoints().getUsedPoints());
		TrainingPackageUpgrade tpu = TrainingPackageUpgrade.builder().build();
		String characterId = character.getId();
		assertThrows(ConstraintViolationException.class, () -> {
			traningPackageUpgradeService.upgrade(auth, characterId, tpu);
		});
	}

	@Test
	void testInvalidTrainingPackageId() throws IOException {
		JwtAuthenticationToken auth = MockAuthentication.mock();
		CharacterCreation request = readRequest();
		CharacterInfo character = service.create(auth, request).share().block();
		assertEquals(61, character.getDevelopmentPoints().getTotalPoints());
		assertEquals(0, character.getDevelopmentPoints().getUsedPoints());
		TrainingPackageUpgrade tpu = TrainingPackageUpgrade.builder()
			.trainingPackageId("error")
			.build();
		Mono<CharacterInfo> share = traningPackageUpgradeService.upgrade(auth, character.getId(), tpu).share();
		assertThrows(BadRequestException.class, () -> {
			share.block();
		});
	}

	private CharacterCreation readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreation.class).readValue(in);
		}
	}

}
