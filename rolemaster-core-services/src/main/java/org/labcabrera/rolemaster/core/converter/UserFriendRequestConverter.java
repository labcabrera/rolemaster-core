package org.labcabrera.rolemaster.core.converter;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.dto.AddFriendRequest;
import org.labcabrera.rolemaster.core.model.User;
import org.labcabrera.rolemaster.core.model.UserFriendRequest;
import org.springframework.stereotype.Component;

@Component
public class UserFriendRequestConverter {

	public UserFriendRequest convert(User user, AddFriendRequest source) {
		return UserFriendRequest.builder()
			.accepted(false)
			.user(user.getId())
			.creation(LocalDateTime.now())
			.friendId(source.getUsername())
			.message(source.getMessage())
			.build();
	}

}
