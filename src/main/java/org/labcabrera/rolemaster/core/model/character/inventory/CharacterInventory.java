package org.labcabrera.rolemaster.core.model.character.inventory;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "characterInventories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterInventory {

	@Id
	private String id;

	private String characterId;

	@Builder.Default
	private List<CharacterItem> items = new ArrayList<>();

	private EntityMetadata metadata;

}
