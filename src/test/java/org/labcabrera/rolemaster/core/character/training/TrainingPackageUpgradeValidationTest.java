package org.labcabrera.rolemaster.core.character.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.TrainingPackageUpgrade;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreationRequest;
import org.labcabrera.rolemaster.core.service.character.TraningPackageUpgradeService;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class TrainingPackageUpgradeValidationTest {

	@Autowired
	private CharacterCreationService service;

	@Autowired
	private TraningPackageUpgradeService traningPackageUpgradeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testMissingTrainingPackageId() throws IOException {
		CharacterCreationRequest request = readRequest();
		CharacterInfo character = service.create(request).share().block();
		assertEquals(61, character.getDevelopmentPoints().getTotalPoints());
		assertEquals(0, character.getDevelopmentPoints().getUsedPoints());
		TrainingPackageUpgrade tpu = TrainingPackageUpgrade.builder()
			.build();
		assertThrows(ConstraintViolationException.class, () -> {
			traningPackageUpgradeService.upgrade(character.getId(), tpu).share().block();
		});
	}

	@Test
	void testInvalidTrainingPackageId() throws IOException {
		CharacterCreationRequest request = readRequest();
		CharacterInfo character = service.create(request).share().block();
		assertEquals(61, character.getDevelopmentPoints().getTotalPoints());
		assertEquals(0, character.getDevelopmentPoints().getUsedPoints());
		TrainingPackageUpgrade tpu = TrainingPackageUpgrade.builder()
			.trainingPackageId("error")
			.build();
		assertThrows(BadRequestException.class, () -> {
			traningPackageUpgradeService.upgrade(character.getId(), tpu).share().block();
		});
	}

	private CharacterCreationRequest readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreationRequest.class).readValue(in);
		}
	}

}
