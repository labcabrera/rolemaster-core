package org.labcabrera.rolemaster.core.service.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.repository.CharacterInfoRepository;
import org.labcabrera.rolemaster.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@SpringBootTest
class CharacterInfoServiceTest {

	@Autowired
	private CharacterInfoService service;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CharacterInfoRepository characterInfoRepository;

	@BeforeEach
	void setUp() {
		User alice = User.builder().id("alice").friends(Arrays.asList("bob")).build();
		User bob = User.builder().id("bob").friends(Arrays.asList("alice")).build();
		User charlie = User.builder().id("charlie").build();

		CharacterInfo aliceC1 = CharacterInfo.builder().name("alice-c-1").owner("alice").build();
		CharacterInfo aliceC2 = CharacterInfo.builder().name("alice-c-2").owner("alice").build();
		CharacterInfo bobC1 = CharacterInfo.builder().name("bob-c-1").owner("bob").build();
		CharacterInfo charlieC1 = CharacterInfo.builder().name("charlie-c-1").owner("charlie").build();

		userRepository.deleteAll()
			.then(characterInfoRepository.deleteAll())
			.thenMany(userRepository.saveAll(Arrays.asList(alice, bob, charlie)))
			.thenMany(characterInfoRepository.saveAll(Arrays.asList(aliceC1, aliceC2, bobC1, charlieC1)))
			.blockLast();
	}

	@Test
	void testFindAll01() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		when(auth.getName()).thenReturn("alice");
		Pageable pagination = mock(Pageable.class);
		when(pagination.getSort()).thenReturn(Sort.by("name"));
		List<CharacterInfo> list = service.findAll(auth, pagination).collectList().share().block();
		assertEquals(3, list.size());
		assertEquals("alice-c-1", list.get(0).getName());
		assertEquals("alice-c-2", list.get(1).getName());
		assertEquals("bob-c-1", list.get(2).getName());
	}

	@Test
	void testFindAll02() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		when(auth.getName()).thenReturn("bob");
		Pageable pagination = mock(Pageable.class);
		when(pagination.getSort()).thenReturn(Sort.by(Direction.DESC, "name"));
		List<CharacterInfo> list = service.findAll(auth, pagination).collectList().share().block();
		assertEquals(3, list.size());
		assertEquals("bob-c-1", list.get(0).getName());
		assertEquals("alice-c-2", list.get(1).getName());
		assertEquals("alice-c-1", list.get(2).getName());
	}

	@Test
	void testFindAll03() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		when(auth.getName()).thenReturn("charlie");
		Pageable pagination = mock(Pageable.class);
		when(pagination.getSort()).thenReturn(Sort.by("name"));
		List<CharacterInfo> list = service.findAll(auth, pagination).collectList().share().block();
		assertEquals(1, list.size());
		assertEquals("charlie-c-1", list.get(0).getName());
	}

	@Test
	void testFindAllAdmin() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		when(auth.getName()).thenReturn("admin");
		when(auth.getAuthorities()).thenReturn(Arrays.asList(new SimpleGrantedAuthority("admin")));
		Pageable pagination = mock(Pageable.class);
		when(pagination.getSort()).thenReturn(Sort.by("name"));
		List<CharacterInfo> list = service.findAll(auth, pagination).collectList().share().block();
		assertEquals(4, list.size());
	}

	@Test
	void testFindById01() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		when(auth.getName()).thenReturn("alice");
		CharacterInfo aliceC1 = characterInfoRepository.findByNameAndOwner("alice-c-1", "alice").share().block();
		CharacterInfo readed = service.findById(auth, aliceC1.getId()).share().block();
		assertEquals("alice-c-1", readed.getName());
	}

	@Test
	void testFindById02() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		when(auth.getName()).thenReturn("bob");
		CharacterInfo aliceC1 = characterInfoRepository.findByNameAndOwner("alice-c-1", "alice").share().block();
		CharacterInfo readed = service.findById(auth, aliceC1.getId()).share().block();
		assertEquals("alice-c-1", readed.getName());
	}

	@Test
	void testInsert() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		when(auth.getName()).thenReturn("alice");
		CharacterInfo aliceC3 = CharacterInfo.builder().name("alice-c-3").build();
		CharacterInfo inserted = service.insert(auth, aliceC3).share().block();
		assertNotNull(inserted);
		assertEquals("alice", inserted.getOwner());
	}

	@Test
	void testUpdate01() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		when(auth.getName()).thenReturn("alice");
		CharacterInfo aliceC1 = characterInfoRepository.findByNameAndOwner("alice-c-1", "alice").share().block();
		aliceC1.setAge(30);
		CharacterInfo updated = service.update(auth, aliceC1).share().block();
		assertNotNull(updated);
		assertEquals("alice", updated.getOwner());
		assertEquals(30, updated.getAge());
	}

	@Test
	void testUpdate02() {
		JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
		when(auth.getName()).thenReturn("bob");
		CharacterInfo aliceC1 = characterInfoRepository.findByNameAndOwner("alice-c-1", "alice").share().block();
		aliceC1.setAge(30);
		try {
			service.update(auth, aliceC1).share().block();
			Assertions.fail("Expected access denied exception");
		}
		catch (AccessDeniedException ex) {
			//Success
		}
	}

}
