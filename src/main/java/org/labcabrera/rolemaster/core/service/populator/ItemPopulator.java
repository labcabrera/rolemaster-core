package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.item.Item;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class ItemPopulator extends AbstractJsonPopulator<Item> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList("data/populator/items.json");
	}

	@Override
	protected TypeReference<List<Item>> getTypeReference() {
		return new TypeReference<List<Item>>() {
		};
	}

}
