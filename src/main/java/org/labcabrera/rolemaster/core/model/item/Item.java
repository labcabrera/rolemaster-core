package org.labcabrera.rolemaster.core.model.item;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Document(collection = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Item {

	@Id
	private String id;

	private String name;

	private ItemType type;

	@Builder.Default
	private Integer count = 1;

	@Schema(description = "Value in hours")
	private Integer craftDuration;

	@Builder.Default
	private Map<CoinType, Integer> commonCost = new EnumMap<>(CoinType.class);

	private Range<BigDecimal> commonWeight;

}
