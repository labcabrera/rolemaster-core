package org.labcabrera.rolemaster.core.service.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.dto.SkillUpgradeRequest;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.CharacterDevelopment;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class CharacterUpdateSkillServiceTest {

	@InjectMocks
	private CharacterUpdateSkillService service;

	@Mock
	private CharacterInfoRepository repository;

	@Spy
	private CharacterInfo character;

	@Spy
	private SkillUpgradeRequest request;

	@BeforeEach
	void prepare() {
		character.setId("c-01");
		character.setSkillCategories(Arrays.asList(CharacterSkillCategory.builder()
			.categoryId("cat-01")
			.developmentCost(Arrays.asList(2, 7))
			.upgradedRanks(1)
			.build()));
		character.setDevelopmentPoints(CharacterDevelopment.builder()
			.remainingPoints(10)
			.build());
		request.setCategoryRanks(Collections.singletonMap("cat-01", 2));
		lenient().when(repository.findById(character.getId())).thenReturn(Mono.just(character));
		lenient().when(repository.save(character)).thenReturn(Mono.just(character));
	}

	@Test
	void testSuccess() {
		Mono<CharacterInfo> mono = service.updateRanks(character.getId(), request);
		CharacterInfo result = mono.share().block();
		assertEquals(3, result.getSkillCategories().stream()
			.filter(e -> e.getCategoryId().equals("cat-01")).findFirst().orElse(null)
			.getUpgradedRanks());
		assertEquals(1, result.getDevelopmentPoints().getRemainingPoints());
		verify(repository, Mockito.times(1)).save(character);
	}

	@Test
	void testRemainingPointException() {
		assertThrows(BadRequestException.class, () -> {
			character.getDevelopmentPoints().setRemainingPoints(8);
			service.updateRanks(character.getId(), request).share().block();
		});
		verify(repository, Mockito.times(0)).save(character);
	}

	@Test
	void testInvalidLevelCount() {
		assertThrows(BadRequestException.class, () -> {
			request.setCategoryRanks(Collections.singletonMap("cat-01", 3));
			service.updateRanks(character.getId(), request).share().block();
		});
		verify(repository, Mockito.times(0)).save(character);
	}

	@Test
	void testInvalidCategoryId() {
		assertThrows(BadRequestException.class, () -> {
			request.setCategoryRanks(Collections.singletonMap("cat-02", 1));
			service.updateRanks(character.getId(), request).share().block();
		});
		verify(repository, Mockito.times(0)).save(character);
	}

}
