package org.labcabrera.rolemaster.core.service.character;

import static com.mongodb.assertions.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.model.character.AttributeType;
import org.labcabrera.rolemaster.core.model.character.CharacterAttribute;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalCharacterService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class TacticalCharacterContextServiceTest {

	@InjectMocks
	private TacticalCharacterService service;

	@Mock
	private TacticalCharacterRepository repository;

	@Mock
	private CharacterService characterService;

	@Spy
	private CharacterInfo characterInfo;

	@Test
	void test() {
		characterInfo.setId("character-01");
		characterInfo.setMaxHp(100);
		characterInfo.getAttributes().put(AttributeType.QUICKNESS, CharacterAttribute.builder().build());

		when(characterService.findById("character-01")).thenReturn(Mono.just(characterInfo));
		when(repository.save(any())).thenAnswer(request -> {
			TacticalCharacter s = request.getArgument(0);
			return Mono.just(TacticalCharacter.builder()
				.id("character-status-01")
				.hp(s.getHp())
				.build());
		});

		Mono<TacticalCharacter> monoStatus = service.create("session-01", "character-01");
		TacticalCharacter status = monoStatus.share().block();

		assertNotNull(status);
		assertEquals("character-status-01", status.getId());
		assertEquals(100, status.getHp().getMax());
	}

	@Test
	void testUserNotFound() {
		when(characterService.findById("character-01")).thenReturn(Mono.empty());

		Mono<TacticalCharacter> monoStatus = service.create("session-01", "character-01");
		TacticalCharacter status = monoStatus.share().block();

		assertNull(status);
	}

}
