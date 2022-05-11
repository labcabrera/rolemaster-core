package org.labcabrera.rolemaster.core.security;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserFriendOwnerProcessor {

	public List<String> filterOwners(User user) {
		List<String> check = new ArrayList<>();
		check.add(user.getId());
		check.addAll(user.getFriends());
		return check;
	}
}
