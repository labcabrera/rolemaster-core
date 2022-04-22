package org.labcabrera.rolemaster.core.model.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ArmorPiece extends Item {

	private ArmorItemType armorType;

	private String armor;

	@Schema(description = "Defensive bonus when equipping with a shield.", required = false)
	private Integer shieldBonus;

}
