package org.labcabrera.rolemaster.core.model.character.inventory;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.item.CoinType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "characterInventories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "List of equipment in possession of the character. This list can range from equipped weapons or armor to items in the trunk at home.")
public class CharacterInventory {

	@Id
	@Schema(description = "Character identifier.", required = true, example = "6242c18da7a9f7048331ca03")
	private String id;

	@Builder.Default
	@Schema(description = "Coins held by the character.", required = true)
	private Map<CoinType, Integer> coins = new EnumMap<>(CoinType.class);

	@Builder.Default
	@Schema(description = "Equipment owned by the character.", required = true)
	private List<CharacterItem> items = new ArrayList<>();

	@Schema(description = "Audit data.", required = true)
	private EntityMetadata metadata;

}
