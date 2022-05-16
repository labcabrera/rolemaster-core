package org.labcabrera.rolemaster.core.service.character;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.model.character.CharacterDevelopment;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.character.CharacterSkill;
import org.labcabrera.rolemaster.core.model.character.CharacterSkillCategory;
import org.labcabrera.rolemaster.core.model.character.RankType;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.service.MetadataModificationUpdater;
import org.labcabrera.rolemaster.core.service.character.processor.CharacterPostProcessorService;
import org.labcabrera.rolemaster.core.service.security.WriteAuthorizationFilter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class CharacterUpdateSkillServiceTest {

	@InjectMocks
	private CharacterUpdateSkillService service;

	@Mock
	private JwtAuthenticationToken auth;

	@Mock
	private CharacterInfoService characterInfoService;

	@Spy
	private WriteAuthorizationFilter writeAuthorizationFilter;

	@Spy
	private MetadataModificationUpdater metadataModificationUpdater;

	@Mock
	private CharacterPostProcessorService postProcessorService;

	@Mock
	private CharacterInfoRepository repository;

	@Spy
	private CharacterInfo character;

	@Spy
	private SkillUpgrade request;

	@BeforeEach
	void prepare() {
		character.setId("c-01");
		character.setSkillCategories(Arrays.asList(CharacterSkillCategory.builder()
			.categoryId("cat-01")
			.developmentCost(Arrays.asList(3, 7))
			.build()));
		character.getSkillCategory("cat-01").get().getRanks().put(RankType.CONSOLIDATED, 1);
		character.setSkills(Arrays.asList(CharacterSkill.builder()
			.skillId("s-01")
			.developmentCost(Arrays.asList(1, 2, 3))
			.build()));
		character.getSkill("s-01").get().getRanks().put(RankType.CONSOLIDATED, 6);
		character.setDevelopmentPoints(CharacterDevelopment.builder()
			.usedPoints(10)
			.totalPoints(100)
			.build());

		request.setCategoryRanks(Collections.singletonMap("cat-01", 2));
		request.setSkillRanks(Collections.singletonMap("s-01", 3));

		when(characterInfoService.findById(auth, character.getId())).thenReturn(Mono.just(character));
	}

	@Test
	void testInvalidLevelCount() {
		request.setCategoryRanks(Collections.singletonMap("cat-01", 3));
		Mono<CharacterInfo> share = service.updateRanks(auth, character.getId(), request).share();
		assertThrows(BadRequestException.class, () -> {
			share.block();
		});
		verify(repository, times(0)).save(character);
	}

	@Test
	void testInvalidCategoryId() {
		request.setCategoryRanks(Collections.singletonMap("cat-02", 1));
		Mono<CharacterInfo> share = service.updateRanks(auth, character.getId(), request).share();
		assertThrows(BadRequestException.class, () -> {
			share.block();
		});
		verify(repository, times(0)).save(character);
	}

}
