package org.labcabrera.rolemaster.core.model.item;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.Range;
import org.labcabrera.rolemaster.core.model.character.item.ItemFeature;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Document(collection = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(of = "id")
public class Item {

	@Id
	private String id;

	private String name;

	private String description;

	private ItemType type;

	@Builder.Default
	private Boolean multipleItem = false;

	@Builder.Default
	private Integer count = 1;

	@Schema(description = "Value in hours")
	private Float craftDuration;

	@Builder.Default
	private Map<CoinType, Integer> commonCost = new EnumMap<>(CoinType.class);

	private Range<BigDecimal> commonWeight;

	private Range<Integer> commonLength;

	private List<ItemFeature> commonFeatures;

	@SuppressWarnings("unchecked")
	public <E extends Item> E as(Class<E> entityClass) {
		try {
			return (E) this;
		}
		catch (Exception ex) {
			throw new DataConsistenceException(String.format("Class %s cannot be casted to class %s.",
				this.getClass().getSimpleName(),
				entityClass.getSimpleName()));
		}
	}

}
