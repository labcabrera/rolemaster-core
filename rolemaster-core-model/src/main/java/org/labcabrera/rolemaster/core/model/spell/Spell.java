package org.labcabrera.rolemaster.core.model.spell;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "spells")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Spell {

	private String id;

	private String spellListId;

	private String name;

	private Integer level;

	private SpellType type;

	private SpellSubtype subType;

	private SpellPreparation preparation;

	private SpellTargetType targetType;

	private BigDecimal targetModifier;

	private SpellDurationType durationType;

	private BigDecimal durationModifier;

	private SpellRangeType rangeType;

	private BigDecimal rangeModifier;

	@Builder.Default
	private BigDecimal ppMultiplier = new BigDecimal("1");

	private String description;
}
