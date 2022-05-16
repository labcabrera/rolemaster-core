package org.labcabrera.rolemaster.core.service.table.armor;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ArmorService {

	@Autowired
	private ObjectMapper objectMapper;

	private Map<Integer, ArmorModifier> armors = new HashMap<>();

	public ArmorModifier getArmorModifier(int armor) {
		return armors.get(armor);
	}

	@PostConstruct
	public void loadData() throws IOException {
		List<ArmorModifier> modifiers = loadTable();
		modifiers.stream().forEach(e -> armors.put(e.getArmor(), e));
	}

	private List<ArmorModifier> loadTable() throws IOException {
		String resource = "data/table/armor/armor-modifiers.json";
		try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
			return objectMapper.readerFor(new TypeReference<List<ArmorModifier>>() {
			}).readValue(in);
		}
	}
}
