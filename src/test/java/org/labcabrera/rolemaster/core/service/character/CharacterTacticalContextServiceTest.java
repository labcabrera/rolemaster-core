package org.labcabrera.rolemaster.core.service.character;

import static com.mongodb.assertions.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.tactical.CharacterTacticalContext;
import org.labcabrera.rolemaster.core.repository.CharacterStatusRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class CharacterTacticalContextServiceTest {

	@InjectMocks
	private CharacterTacticalContextService service;

	@Mock
	private CharacterStatusRepository repository;

	@Mock
	private CharacterService characterService;

	@Test
	void test() {
		when(characterService.findById("character-01")).thenReturn(Mono.just(CharacterInfo.builder()
			.id("character-01")
			.maxHp(100)
			.name("Test")
			.build()));
		when(repository.save(any())).thenAnswer(request -> {
			CharacterTacticalContext s = request.getArgument(0);
			return Mono.just(CharacterTacticalContext.builder()
				.id("character-status-01")
				.hp(s.getHp())
				.build());
		});

		Mono<CharacterTacticalContext> monoStatus = service.create("session-01", "character-01");
		CharacterTacticalContext status = monoStatus.share().block();

		assertNotNull(status);
		assertEquals("character-status-01", status.getId());
		assertEquals(100, status.getHp());
	}

	@Test
	void testUserNotFound() {
		when(characterService.findById("character-01")).thenReturn(Mono.empty());

		Mono<CharacterTacticalContext> monoStatus = service.create("session-01", "character-01");
		CharacterTacticalContext status = monoStatus.share().block();

		assertNull(status);
	}

}
