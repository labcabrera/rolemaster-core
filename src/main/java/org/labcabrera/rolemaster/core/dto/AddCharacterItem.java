package org.labcabrera.rolemaster.core.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddCharacterItem {

	@NotBlank
	private String itemId;

	@NotNull
	private ItemPosition position;

	@Schema(description = "Item weight. If not provided, it will be calculated from the common range determined by the type of item.", required = false)
	private BigDecimal weight;

}
