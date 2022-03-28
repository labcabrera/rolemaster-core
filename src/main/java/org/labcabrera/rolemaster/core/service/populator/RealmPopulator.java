package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.Realm;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class RealmPopulator extends AbstractJsonPopulator<Realm> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList("data/populator/realms.json");
	}

	@Override
	protected TypeReference<List<Realm>> getTypeReference() {
		return new TypeReference<List<Realm>>() {
		};
	}
}
