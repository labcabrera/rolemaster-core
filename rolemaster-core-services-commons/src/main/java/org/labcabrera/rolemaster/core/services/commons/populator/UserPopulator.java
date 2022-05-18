package org.labcabrera.rolemaster.core.services.commons.populator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.User;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class UserPopulator extends AbstractJsonPopulator<User> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList("data/populator/users.json");
	}

	@Override
	protected List<User> collectValues() {
		List<User> users = super.collectValues();
		users.stream().forEach(e -> e.setCreated(LocalDateTime.now()));
		return users;
	}

	@Override
	protected TypeReference<List<User>> getTypeReference() {
		return new TypeReference<List<User>>() {
		};
	}
}
