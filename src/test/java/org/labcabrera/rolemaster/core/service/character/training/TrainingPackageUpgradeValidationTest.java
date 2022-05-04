package org.labcabrera.rolemaster.core.service.character.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.dto.TrainingPackageUpgrade;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterCreation;
import org.labcabrera.rolemaster.core.service.character.TrainingPackageUpgradeService;
import org.labcabrera.rolemaster.core.service.character.creation.CharacterCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		CharacterCreation request = readRequest();
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
		CharacterCreation request = readRequest();
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

	private CharacterCreation readRequest() throws IOException {
		try (InputStream in = Thread.currentThread().getContextClassLoader()
			.getResourceAsStream("openapi/examples/character-creation-example-01.json")) {
			return objectMapper.readerFor(CharacterCreation.class).readValue(in);
		}
	}

}
