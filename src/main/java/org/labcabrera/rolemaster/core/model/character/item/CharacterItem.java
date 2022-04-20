package org.labcabrera.rolemaster.core.model.character.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.item.ArmorItemType;
import org.labcabrera.rolemaster.core.model.item.ItemFeature;
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

	private ArmorItemType armorType;

	private String name;

	private Integer bonus;

	private ItemPosition position;

	private BigDecimal weight;

	@Builder.Default
	private Integer count = 1;

	@Builder.Default
	private List<ItemFeature> features = new ArrayList<>();

	@Builder.Default
	private Boolean broken = false;

	//TODO define
	private Map<String, String> customizations;

	private String description;

	@Builder.Default
	private EntityMetadata metadata = new EntityMetadata();

}
