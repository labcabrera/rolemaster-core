package org.labcabrera.rolemaster.core.model.character.item;

import java.math.BigDecimal;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CharacterItem {

	@Id
	private String id;

	private String characterId;

	private String itemId;

	private ItemType type;

	private String name;

	private ItemPosition position;

	private BigDecimal weight;

	@Builder.Default
	private Boolean broken = false;

	//TODO define
	private Map<String, String> customizations;

}
