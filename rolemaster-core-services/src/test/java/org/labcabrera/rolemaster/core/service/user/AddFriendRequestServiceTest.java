package org.labcabrera.rolemaster.core.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.labcabrera.rolemaster.core.dto.AddFriendRequest;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.exception.NotFoundException;
import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.UserFriendRequest;
import org.labcabrera.rolemaster.core.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import reactor.core.publisher.Mono;

@SpringBootTest
class AddFriendRequestServiceTest {

	@Autowired
	private AddFriendRequestService service;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		User alice = User.builder().id("alice").friends(Arrays.asList("dave")).blockedUsers(Arrays.asList("eva")).build();
		User bob = User.builder().id("bob").build();
		User dave = User.builder().id("dave").friends(Arrays.asList("alice")).build();
		User eva = User.builder().id("eva").build();
		userRepository.deleteAll()
			.thenMany(userRepository.saveAll(Arrays.asList(alice, bob, dave, eva)))
			.blockLast();
	}

	@Test
	void testValid() {
		JwtAuthenticationToken auth = Mockito.mock(JwtAuthenticationToken.class);
		Mockito.when(auth.getName()).thenReturn("alice");
		AddFriendRequest request = AddFriendRequest.builder()
			.username("bob")
			.build();
		UserFriendRequest entity = service.apply(auth, request).share().block();
		assertEquals("alice", entity.getUser());
		assertEquals("bob", entity.getFriendId());
	}

	@Test
	void testInvalid01() {
		JwtAuthenticationToken auth = Mockito.mock(JwtAuthenticationToken.class);
		Mockito.when(auth.getName()).thenReturn("alice");
		AddFriendRequest request = AddFriendRequest.builder()
			.build();
		assertThrows(ConstraintViolationException.class, () -> service.apply(auth, request));
	}

	@ParameterizedTest
	@CsvSource({
		"alice, alice",
		"eva, alice",
		"alice, dave"
	})
	void testInvalid02(String source, String target) {
		JwtAuthenticationToken auth = Mockito.mock(JwtAuthenticationToken.class);
		Mockito.when(auth.getName()).thenReturn(source);
		AddFriendRequest request = AddFriendRequest.builder()
			.username(target)
			.build();
		Mono<UserFriendRequest> share = service.apply(auth, request).share();
		assertThrows(BadRequestException.class, () -> share.block());
	}

	@Test
	void testInvalid05() {
		JwtAuthenticationToken auth = Mockito.mock(JwtAuthenticationToken.class);
		Mockito.when(auth.getName()).thenReturn("alice");
		AddFriendRequest request = AddFriendRequest.builder()
			.username("username-not-found")
			.build();
		Mono<UserFriendRequest> share = service.apply(auth, request).share();
		assertThrows(NotFoundException.class, () -> share.block());
	}

}
